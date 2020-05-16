using System;
using System.ComponentModel.DataAnnotations;

namespace RollCall.Models
{
    public enum LeaveType
    {
        Annual,
        Sick,
        Unpaid,
        Study,
        Maternity,
        FamilyResponsibility
    };

    public enum LeaveStatus
    {
        New,
        Approved,
        Rejected    
    }

    public enum FileType
    {
        Avatar = 1,
        Photo
    }
    public class Leave
    {
        public int Id { get; set; }
        public DateTime From { get; set; } = new DateTime();
        public DateTime To { get; set; } = new DateTime();
        public LeaveType Type { get; set; }
        public string Motivation { get; set; }
        public int NumberOfDays { get; set; }
        public LeaveStatus LeaveStatus { get; set; }
        public User User { get; set; } = new User();
    }

    public class LeaveRequestPayLoad
    {
        public Leave Leave { get; set; } = new Leave();
        public File Attschment { get; set; } = new File();
    }
    public class File
    {
        public int Id { get; set; }
        [StringLength(255)]
        public string Name { get; set; }
        [StringLength(100)]
        public string ContentType { get; set; }
        public byte[] Content { get; set; }
        public FileType Type { get; set; }
        public Leave Leave { get; set; } = new Leave();
    }

    public class LeaveBalance
    {
        public int Id { get; set; }
        public decimal Annual { get; set; }
        public decimal Sick { get; set; }
        public decimal Study { get; set; }
        public decimal Maternity { get; set; }
    }
}
