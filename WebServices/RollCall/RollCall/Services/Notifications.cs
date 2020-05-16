using Microsoft.Extensions.Options;
using RollCall.Data_Context;
using RollCall.IServices;
using RollCall.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Mail;
using System.Threading.Tasks;

namespace RollCall.Services
{
    public class Notifications:INotifications
    {
        private RollCallDataContext _context;
        private AppSettings _appSettings;
        public Notifications(RollCallDataContext context, IOptions<AppSettings> appSettings)
        {
            _context = context;
            _appSettings = appSettings.Value;
        }

        public async void FailedEmail(Email email)
        {
            _context.Email.Update(email);
            await _context.SaveChangesAsync();
        }

        public async void SendEmail(Email email)
        {
            SmtpClient smtpClient = new SmtpClient();

            MailMessage message = new MailMessage();
            MailAddress toAddress = new MailAddress(email.To);

            smtpClient.Host = _appSettings.EmailConfig.Host;
            smtpClient.Port = _appSettings.EmailConfig.Port;
            smtpClient.EnableSsl = true;
            smtpClient.UseDefaultCredentials = false;
            smtpClient.Credentials = new NetworkCredential(_appSettings.EmailConfig.Username, _appSettings.EmailConfig.Password);
            smtpClient.DeliveryMethod = SmtpDeliveryMethod.Network;
            smtpClient.Timeout = 20000;

            message.From = new MailAddress(_appSettings.EmailConfig.From);
            message.To.Add(toAddress);
            message.IsBodyHtml = true;
            message.Subject = email.Subject;
            message.Body = email.body;

            await smtpClient.SendMailAsync(message);
        }
    }
}
