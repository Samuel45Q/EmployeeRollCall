using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace RollCall.Models
{
    public class AppSettings
    {
        public int ExpiryNumberOfDays { get; set; }
        public string Secret { get; set; }
        public EmailConfig EmailConfig { get; set; } = new EmailConfig();
    }
}
