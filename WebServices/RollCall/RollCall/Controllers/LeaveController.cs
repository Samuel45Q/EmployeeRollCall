using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Options;
using RollCall.Models;
using RollCall.IServices;
using RollCall.Services;
using Microsoft.AspNetCore.Authorization;
using AutoMapper;
using RollCall.Helpers;

namespace RollCall.Controllers
{
    [Authorize]
    [Route("api/[controller]")]
    [ApiController]
    public class LeaveController : ControllerBase
    {
        private ILeaveManager _leaveManager;
        private IMapper _mapper;
        private readonly AppSettings _appSettings;
        private IGeoLocation _geoServ;
        public LeaveController(ILeaveManager leaveManager, IMapper mapper, IOptions<AppSettings> appSettings, IGeoLocation  geo)
        {
            _leaveManager = leaveManager;
            _mapper = mapper;
            _appSettings = appSettings.Value;
            _geoServ = geo;
        }



        [AllowAnonymous]
        [HttpPost("Apply")]
        public IActionResult Apply([FromBody]LeaveRequestPayLoad leaveRequestPayLoad)
        {
            try
            {
               var loadedLeave= _leaveManager.CreateLeaveTicket(leaveRequestPayLoad);
                return Ok(new
                {
                    Status = true,
                    Message = $"Your {leaveRequestPayLoad.Leave.Type} Leave has been loaded successfully with {loadedLeave.NumberOfDays} days"
                });
            }
            catch (AppException ex)
            {
                return BadRequest(new { message = ex.Message });
            }
        }


        [AllowAnonymous]
        [HttpPost("Approve")]
        public IActionResult Approve([FromBody]Leave leave)
        {
            try
            {
               return Ok(_leaveManager.Approve_Decline(leave));
            }
            catch (AppException ex)
            {
                return BadRequest(new { message = ex.Message });
            }
        }


        [AllowAnonymous]
        [HttpPost("Geo")]
        public IActionResult Geo([FromBody]Geo geo)
        {
            try
            {
                return Ok(_geoServ.RetrieveFormatedAddress(geo.lt,geo.lng));
            }
            catch (AppException ex)
            {
                return BadRequest(new { message = ex.Message });
            }
        }

    }


}