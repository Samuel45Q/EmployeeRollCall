using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace RollCall.Models
{
    public class User
    {
        public int Id { get; set; }
        public string FirstName { get; set; }
        public string SecondName { get; set; }
        public string LastName { get; set; }
        public string NationalId { get; set; }
        public string Email { get; set; }
        public string Phone { get; set; }
        public string StreetNo { get; set; }
        public string Surbub { get; set; }
        public string City { get; set; }
        public string Role { get; set; }
        public string Province { get; set; }
        public string Username { get; set; }
        public string Password { get; set; }
        public byte[] PasswordHash { get; set; }
        public byte[] PasswordSalt { get; set; }
        public LeaveBalance LeaveBalance { get; set; } = new LeaveBalance();
    }


    public class Credentials
    {
        public int Id { get; set; }
        public string Username { get; set; }
        public string Password { get; set; }
    }
}
