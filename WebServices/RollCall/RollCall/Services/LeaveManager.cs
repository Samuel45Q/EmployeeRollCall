using Microsoft.EntityFrameworkCore;
using RollCall.Data_Context;
using RollCall.Helpers;
using RollCall.IServices;
using RollCall.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace RollCall.Services
{
    public class LeaveManager : ILeaveManager
    {
        private RollCallDataContext _context;

        public LeaveManager(RollCallDataContext context)
        {
            _context = context;
        }
        public  Leave CreateLeaveTicket(LeaveRequestPayLoad leaveRequestPayload)
        {

            if(leaveRequestPayload.Leave.User==null) throw new AppException("Employee does not exist");
            if (leaveRequestPayload.Leave.From > leaveRequestPayload.Leave.To) throw new AppException("Last date of leave can not be less than start date of leave");
            if (leaveRequestPayload.Leave.Type.Equals(LeaveType.Sick) && leaveRequestPayload.Attschment==null) throw new AppException("Medical Proof is required for sick leave");
            if (leaveRequestPayload.Leave.Type.Equals(LeaveType.FamilyResponsibility) && IsFamilyResponsibilityValidater(leaveRequestPayload.Leave.Type, leaveRequestPayload.Leave.Motivation)) throw new AppException("Medical Proof is required for sick leave");

            leaveRequestPayload.Leave.User = _context.User.AsNoTracking().Include((l)=>l.LeaveBalance).FirstOrDefault((u)=>u.Id.Equals(leaveRequestPayload.Leave.User.Id));
            if(leaveRequestPayload.Leave.Type.Equals(LeaveType.Annual) && leaveRequestPayload.Leave.User.LeaveBalance.Annual<1) throw new AppException($"You do not have enough annual leave days to apply. Balance {leaveRequestPayload.Leave.User.LeaveBalance.Annual}");

            leaveRequestPayload.Leave.NumberOfDays = NumberOfWorkDays(leaveRequestPayload.Leave.From, leaveRequestPayload.Leave.To);

            if (leaveRequestPayload.Attschment != null)
            {
                _context.File.Update(leaveRequestPayload.Attschment);
                _context.SaveChanges();
            }
            else
            {
                _context.Leave.Update(leaveRequestPayload.Leave);
                _context.SaveChanges();
            }

            return leaveRequestPayload.Leave;
        }

        private int NumberOfWorkDays(DateTime start, DateTime end)
        {
            int workDays = 0;

            while (start != end)
            {
                if (start.DayOfWeek != DayOfWeek.Saturday && start.DayOfWeek != DayOfWeek.Sunday && !_context.PublicHolidays.Select(x=>x.HolidayDate).ToList().Contains(Convert.ToDateTime(start.ToString("yyyy-MM-dd"))))
                {
                    workDays++;
                }
                start = start.AddDays(1);
            }
            return workDays + 1;
        }


        public object Approve_Decline(Leave leave)
        {
            var requestedLeave = _context.Leave.AsNoTracking().Include((u)=>u.User.LeaveBalance).FirstOrDefault((l)=>l.Id.Equals(leave.Id));
            if (leave.LeaveStatus.Equals(1)) 
            { 
                requestedLeave.LeaveStatus = LeaveStatus.Approved;
                requestedLeave.User.LeaveBalance = GetLeaveBalance_After_Approval(requestedLeave);
            }

            if (leave.LeaveStatus.Equals(2)) { requestedLeave.LeaveStatus = LeaveStatus.Rejected; }

            _context.Update(requestedLeave);
            _context.SaveChanges();
            return new { Status = true, Message = $"Leave request REQ-{requestedLeave.Id} for Employee {requestedLeave.User.FirstName} {requestedLeave.User.LastName} approved successfully" };
        }

        private LeaveBalance GetLeaveBalance_After_Approval(Leave leave)
        {
            switch(leave.Type)
            {
                case LeaveType.Annual:
                    leave.User.LeaveBalance.Annual= leave.User.LeaveBalance.Annual - leave.NumberOfDays;
                    break;
                case LeaveType.Sick:
                    leave.User.LeaveBalance.Sick = leave.User.LeaveBalance.Sick - leave.NumberOfDays;
                    break;
                case LeaveType.Study:
                    leave.User.LeaveBalance.Study = leave.User.LeaveBalance.Study - leave.NumberOfDays;
                    break;
                case LeaveType.Maternity:
                    leave.User.LeaveBalance.Maternity = leave.User.LeaveBalance.Maternity - leave.NumberOfDays;
                    break;
            }
            return leave.User.LeaveBalance;
        }

        private bool IsFamilyResponsibilityValidater(LeaveType type,string motivation)
        {
            if (motivation.Contains("Child") || motivation.Contains("Father") || motivation.Contains("Mother") || motivation.Contains("Husband") || motivation.Contains("Wife") || motivation.Contains("Death")) return false;
            return true;
        }
    }
}
