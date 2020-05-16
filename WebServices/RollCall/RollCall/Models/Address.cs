using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace RollCall.Models
{
    public class Address
    {
        public string Street { get; set; }
        public string Town { get; set; }
    }



    public class Geo
    {
        public string lt { get; set; }
        public string lng { get; set; }
    }
}
