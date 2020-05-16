using AutoMapper.Configuration;
using Microsoft.AspNetCore.Http;
using RollCall.Data_Context;
using RollCall.IServices;
using RollCall.Models;
using System.Linq;
using System.Net;
using System.Xml.Linq;

namespace RollCall.Services
{
    public class GeoLocation : IGeoLocation
    {
        private RollCallDataContext _context;
        public GeoLocation()
        {
        }

        public Address RetrieveFormatedAddress(string lat, string lng)
        {
            Address address = new Address();


            string baseUri =$"http://maps.googleapis.com/maps/api/geocode/xml?latlng={lat},{lng}&sensor=false";
            string location = string.Empty;

            string requestUri = string.Format(baseUri, lat, lng);

            using (WebClient wc = new WebClient())
            {
                string result = wc.DownloadString(requestUri);
                var xmlElm = XElement.Parse(result);
                var status = (from elm in xmlElm.Descendants()where elm.Name == "status" select elm).FirstOrDefault();

                if (status.Value.ToLower() == "ok")
                {
                    var res = (from elm in xmlElm.Descendants() where elm.Name == "formatted_address" select elm).FirstOrDefault();
                    requestUri = res.Value;
                }
            }

            return address;
        }
    }
}
