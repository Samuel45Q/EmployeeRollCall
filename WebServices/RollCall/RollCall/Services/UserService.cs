using Microsoft.EntityFrameworkCore;
using RollCall.Data_Context;
using RollCall.Helpers;
using RollCall.IServices;
using RollCall.Models;
using System;
using System.Collections.Generic;
using System.Linq;

namespace RollCall.Services
{
    public class UserService : IUserService
    {
        private RollCallDataContext _context;

        public UserService(RollCallDataContext context)
        {
            _context = context;
        }

        public User Authenticate(string username, string password)
        {
            if (string.IsNullOrEmpty(username) || string.IsNullOrEmpty(password))
                return null;

            var user = _context.User.AsNoTracking().Include((u)=>u.LeaveBalance).SingleOrDefault(x => x.Username.Equals(username));

            // check if username exists
            if (user == null)
                return null;

            // check if password is correct
            if (!VerifyPasswordHash(password, user.PasswordHash, user.PasswordSalt))
                return null;
            // authentication successful
            return user;
        }


        public IEnumerable<User> GetAll()
        {
            return _context.User.ToList();
        }

        public User GetById(int id)
        {
            return _context.User.Find(id);
        }

        public User Create(User user, string password)
        {
            // validation
            if (string.IsNullOrWhiteSpace(password))
                throw new AppException("Password is required");

            if (_context.User.Any(x => x.Username.Equals(user.Username)))
                throw new AppException($"Username {user.Username} is already taken");

            if (_context.User.Any(x => x.NationalId == user.NationalId))
                throw new AppException("Employee with ID Number " + user.NationalId + " already exist");

            byte[] passwordHash, passwordSalt;
            CreatePasswordHash(password, out passwordHash, out passwordSalt);

            user.PasswordHash = passwordHash;
            user.PasswordSalt = passwordSalt;



            LeaveBalance leaveBalance = new LeaveBalance()
            {
                Annual = 15,
                Sick = 30,
                Maternity = 60,
                Study = 12,
            };

            user.LeaveBalance = leaveBalance;

            _context.User.Add(user);
            _context.SaveChanges();

            return user;
        }



        public object GetUserRequestedLeaves(int Id)
        {
            return _context.Leave.Where((u) => u.User.Id.Equals(Id)).Select(x => new { x.Id, x.NumberOfDays, x.From, x.To, x.Motivation, x.LeaveStatus });
        }


        public User ChangePasswordOTP_PartOne(ForgotPassword forgotPassword)
        {
            if(!(forgotPassword.NewPassword.Equals(forgotPassword.ConfirmPassword))) throw new AppException("Passwords do not match.");
            if (string.IsNullOrWhiteSpace(forgotPassword.NationalId) && (string.IsNullOrWhiteSpace(forgotPassword.eMail) || string.IsNullOrWhiteSpace(forgotPassword.Username))) throw new AppException("Please provide Natioal ID number with Email or username.");

            _context.ForgotPassword.Update(forgotPassword);
             _context.SaveChangesAsync();

            return _context.User.FirstOrDefault((u)=>u.NationalId.Equals(forgotPassword.NationalId));
        }


        public async void ChangePasswordOTP_PartTwo(ForgotPassword forgotPassword)
        {
            if (_context.ForgotPassword.FirstOrDefault((p)=>p.Otp.Equals(forgotPassword.Otp) && p.NationalId.Equals(forgotPassword.NationalId) && (p.Username.Equals(forgotPassword.Username) || p.eMail.Equals(forgotPassword.eMail)))==null) throw new AppException("You have entered incorrect OTP.");

            var user = _context.User.FirstOrDefault((u)=>u.NationalId.Equals(forgotPassword.NationalId) && u.Username.Equals(forgotPassword.Username));

            Update(user,forgotPassword.NewPassword);
            await _context.SaveChangesAsync();
        }


        public void Update(User userParam, string password = null)
        {
            var user = _context.User.Find(userParam.Id);

            if (user == null)
                throw new AppException("User not found");

            if (userParam.Username != user.Username)
            {
                // username has changed so check if the new username is already taken
                if (_context.User.Any(x => x.Username == userParam.Username))
                    throw new AppException("Username " + userParam.Username + " is already taken");
            }



            // update user properties
            user.FirstName = userParam.FirstName;
            user.LastName = userParam.LastName;
            user.Username = userParam.Username;

            // update password if it was entered
            if (!string.IsNullOrWhiteSpace(password))
            {
                byte[] passwordHash, passwordSalt;
                CreatePasswordHash(password, out passwordHash, out passwordSalt);

                user.PasswordHash = passwordHash;
                user.PasswordSalt = passwordSalt;
            }

            _context.User.Update(user);
            _context.SaveChanges();
        }

        public string GetOTP()
        {
            var chars1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
            var stringChars1 = new char[6];
            var random1 = new Random();

            for (int i = 0; i < stringChars1.Length; i++)
            {
                stringChars1[i] = chars1[random1.Next(chars1.Length)];
            }

           return new String(stringChars1);
        }

        public void Delete(int id)
        {
            var user = _context.User.Find(id);
            if (user != null)
            {
                _context.User.Remove(user);
                _context.SaveChanges();
            }
        }



        public bool IsSouthAfricanIdentityNumber(string Id)
        {
            if (!Id.Length.Equals(13)) return false;
            return true;
        }

        // private helper methods

        private static void CreatePasswordHash(string password, out byte[] passwordHash, out byte[] passwordSalt)
        {
            if (password == null) throw new ArgumentNullException("password");
            if (string.IsNullOrWhiteSpace(password)) throw new ArgumentException("Value cannot be empty or whitespace only string.", "password");

            using (var hmac = new System.Security.Cryptography.HMACSHA512())
            {
                passwordSalt = hmac.Key;
                passwordHash = hmac.ComputeHash(System.Text.Encoding.UTF8.GetBytes(password));
            }
        }

        private static bool VerifyPasswordHash(string password, byte[] storedHash, byte[] storedSalt)
        {
            if (password == null) throw new ArgumentNullException("password");
            if (string.IsNullOrWhiteSpace(password)) throw new ArgumentException("Value cannot be empty or whitespace only string.", "password");
            if (storedHash.Length != 64) throw new ArgumentException("Invalid length of password hash (64 bytes expected).", "passwordHash");
            if (storedSalt.Length != 128) throw new ArgumentException("Invalid length of password salt (128 bytes expected).", "passwordHash");

            using (var hmac = new System.Security.Cryptography.HMACSHA512(storedSalt))
            {
                var computedHash = hmac.ComputeHash(System.Text.Encoding.UTF8.GetBytes(password));
                for (int i = 0; i < computedHash.Length; i++)
                {
                    if (computedHash[i] != storedHash[i]) return false;
                }
            }

            return true;
        }


    }
}
