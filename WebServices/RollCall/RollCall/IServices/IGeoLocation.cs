using RollCall.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace RollCall.IServices
{
    public interface IGeoLocation
    {
        Address RetrieveFormatedAddress(string lat, string lng);
    }
}
