using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace RollCall.Models
{
    public class ForgotPassword
    {
        public int Id { get; set; }
        public string Username { get; set; }
        public string eMail { get; set; }
        public string NationalId { get; set; }
        public string Otp { get; set; }
        public string NewPassword { get; set; }
        public string ConfirmPassword { get; set; }
    }
}
