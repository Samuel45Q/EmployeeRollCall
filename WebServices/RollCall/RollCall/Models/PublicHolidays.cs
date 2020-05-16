using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace RollCall.Models
{
    public class PublicHolidays
    {
        public int Id { get; set; }
        public DateTime HolidayDate { get; set; } = new DateTime();
        public string HolidayName { get; set; }
    }
}
