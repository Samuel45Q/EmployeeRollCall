using System;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using System.IdentityModel.Tokens.Jwt;
using Microsoft.Extensions.Options;
using System.Text;
using Microsoft.IdentityModel.Tokens;
using System.Security.Claims;
using RollCall.Models;
using AutoMapper;
using RollCall.IServices;
using RollCall.Helpers;
using System.Linq;
using System.Collections.Generic;

namespace RollCall.Controllers
{
    [Authorize]
    [Route("api/[controller]")]
    [ApiController]
    public class UserController : ControllerBase
    {
        private IUserService _userService;
        private INotifications _notifications;
        private IMapper _mapper;
        private readonly AppSettings _appSettings;

        public UserController(IUserService userService,INotifications notifications, IMapper mapper, IOptions<AppSettings> appSettings)
        {
            _userService = userService;
            _notifications = notifications;
            _mapper = mapper;
            _appSettings = appSettings.Value;
        }


        [AllowAnonymous]
        [HttpPost("Login")]
        public IActionResult Login([FromBody]Credentials auth)
        {
            var user = _userService.Authenticate(auth.Username, auth.Password);
            if (user==null) return BadRequest(new { Status=false,
                message = "Username or password is incorrect" });

            var tokenHandler = new JwtSecurityTokenHandler();
            var key = Encoding.ASCII.GetBytes(_appSettings.Secret);
            var tokenDescriptor = new SecurityTokenDescriptor
            {
                Subject = new ClaimsIdentity(new Claim[]
                {
                    new Claim(ClaimTypes.Name, user.Id.ToString())
                }),
                Expires = DateTime.UtcNow.AddDays(_appSettings.ExpiryNumberOfDays),
                SigningCredentials = new SigningCredentials(new SymmetricSecurityKey(key), SecurityAlgorithms.HmacSha256Signature)
            };
            var token = tokenHandler.CreateToken(tokenDescriptor);
            var tokenString = tokenHandler.WriteToken(token);



            List<User> userLIst = new List<User>();
            userLIst.Add(user);

            var userResponse = userLIst.Select((u) => new {
                u.Id,
                u.FirstName,
                u.LastName,
                u.NationalId,
                u.Email,
                u.Phone,
                u.StreetNo,
                u.Surbub,
                u.City,
                u.Role,
                u.Province,
                u.Username,
                Status = true,
                u.LeaveBalance,
                RequestedLeave=_userService.GetUserRequestedLeaves(u.Id)
            }).First();

            return Ok(new
            {
                User= userResponse,
                Token = tokenString
            });
        }


        [AllowAnonymous]
        [HttpPost("Registration")]
        public IActionResult Registration([FromBody]User userDetails)
        {
            var user = _mapper.Map<User>(userDetails);
            try
            {
                int UserId = _userService.Create(user, userDetails.Password).Id;
                return Ok(new
                {
                    Status = true,
                    Message = $"Account created successfully, Your Employee Number is E-{UserId}"
                }); ;
            }
            catch (AppException ex)
            {
                return BadRequest(new {Status =false, message = ex.Message });
            }
        }


        [AllowAnonymous]
        [HttpPost("GetOTP")]
        public IActionResult GetOTP([FromBody]ForgotPassword forgotPassword)
        {
            try
            {
                forgotPassword.Otp = _userService.GetOTP();
                var user= _userService.ChangePasswordOTP_PartOne(forgotPassword);
                Email email = new Email()
                {
                    To= user.Email,
                    Subject ="Password Resert OTP",
                    body=$"<p>Hi {user.LastName} {user.FirstName}</p><br /><br/><p>Please find an OTP - {forgotPassword.Otp} to resert your password valid for 10 minutes</p><br /><br /> <p>Regards</><br /><p>Team<br />"
                };

                _notifications.SendEmail(email);
                return Ok(new
                {
                    Message = $"Your OTP is {forgotPassword.Otp}"
                });
            }
            catch (AppException ex)
            {
                return BadRequest(new { message = ex.Message });
            }
        }

        [AllowAnonymous]
        [HttpPost("ChangePassword")]
        public IActionResult ChangePassword([FromBody]ForgotPassword forgotPassword)
        {
            try
            {
                _userService.ChangePasswordOTP_PartTwo(forgotPassword);
                return Ok(new
                {
                    Message = $"Password updated successfully"
                });
            }
            catch (AppException ex)
            {
                return BadRequest(new { message = ex.Message });
            }
        }

    }
}