using Microsoft.EntityFrameworkCore;
using RollCall.Models;

namespace RollCall.Data_Context
{
    public class RollCallDataContext : DbContext
    {
        public RollCallDataContext(DbContextOptions<RollCallDataContext> options)
: base(options)
        { }
        public DbSet<User> User { get; set; }
        public DbSet<Leave> Leave { get; set; }
        public DbSet<LeaveBalance> LeaveBalance { get; set; }
        public DbSet<Email> Email { get; set; }
        public DbSet<File> File { get; set; }
        public DbSet<ForgotPassword> ForgotPassword { get; set; }
        public DbSet<PublicHolidays> PublicHolidays { get; set; }
    }
}
