using AutoMapper;
using RollCall.Models;

namespace RollCall.Helpers
{
    public class AutoMapperProfile : Profile
    {
        public AutoMapperProfile()
        {
            CreateMap<User, Credentials>();
            CreateMap<Credentials, User>();
        }
    }
}
