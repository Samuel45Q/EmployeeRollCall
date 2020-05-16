using RollCall.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace RollCall.IServices
{
    public interface ILeaveManager
    {
        Leave CreateLeaveTicket(LeaveRequestPayLoad leaveRequestPayLoad);
        object Approve_Decline(Leave leave);
        //bool IsFamilyResponsibilityValidater(LeaveType type, string motivation);
        //LeaveBalance GetLeaveBalance_After_Approval(Leave leave);
    }
}
