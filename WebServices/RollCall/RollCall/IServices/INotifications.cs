using RollCall.Data_Context;
using RollCall.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace RollCall.IServices
{
    public interface INotifications
    {
        void FailedEmail(Email email);
        void SendEmail(Email email);
    }
}
