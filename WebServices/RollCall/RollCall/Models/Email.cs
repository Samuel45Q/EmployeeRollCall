using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace RollCall.Models
{
    public class Email
    {
        public int Id { get; set; }
        public string To { get; set; }
        public string Subject { get; set; }
        public string body { get; set; }
        public DateTime sentTime { get; set; } = new DateTime();
    }


    public class EmailConfig
    {
        public string Username { get; set; }
        public string Password { get; set; }
        public string Host { get; set; }
        public int Port { get; set; }
        public string From { get; set; }
    }
}
