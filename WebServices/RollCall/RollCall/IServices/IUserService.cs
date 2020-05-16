using RollCall.Data_Context;
using RollCall.Helpers;
using RollCall.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace RollCall.IServices
{

    public interface IUserService
    {
        User Authenticate(string username, string password);
        IEnumerable<User> GetAll();
        User GetById(int id);
        User Create(User user, string password);
        void Update(User user, string password = null);
        void Delete(int id);
        string GetOTP();
        User ChangePasswordOTP_PartOne(ForgotPassword forgotPassword);
        void ChangePasswordOTP_PartTwo(ForgotPassword forgotPassword);

        bool IsSouthAfricanIdentityNumber(string Id);
        object GetUserRequestedLeaves(int Id);
    }
}
