package org.acme.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.model.CompanyNews;
import org.acme.service.CompanyNewsService;

import java.util.List;
import java.util.Optional;

@Path("/companyNews")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CompanyNewsController {

    @Inject
    CompanyNewsService companyNewsService;
    
    @GET
    public List<CompanyNews> getAllCompanyNews() {
        return companyNewsService.getAllCompanyNews();
    }
     
    @GET
    @Path("/companyNews1/{companyNewsId}")
    public Response getCompanyNewsByNewsId(@PathParam("companyNewsId") Long newsId) {
        try {
            List<CompanyNews> companyNews = companyNewsService.getCompanyNewsById(newsId);
            if(companyNews.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\":\"No Company News history found for News ID " + newsId + "\"}")
                        .build();
            }
            return Response.ok(companyNews).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}")
                    .build();
        }
    }
      
    @GET
    @Path("/companyNews2/{adminId}")
    public Response getCompanyNewsByAdminId(@PathParam("adminId") Long userId) {
        try {
            List<CompanyNews> companyNews = companyNewsService.getCompanyNewsByUserId(userId);
            if(companyNews.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\":\"No Company News history found for User Admin Id " + userId + "\"}")
                        .build();
            }
            return Response.ok(companyNews).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}")
                    .build();
        }
    }
    
    @GET
    @Path("/companyNews3/{status}")
    public Response getCompanyNewsByStatus(@PathParam("status") String status) {
        try {
            List<CompanyNews> companyNews = companyNewsService.getCompanyNewsByStatus(status);
            if(companyNews.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\":\"No Company News history found by Status " + status + "\"}")
                        .build();
            }
            return Response.ok(companyNews).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}")
                    .build();
        }
    }
}
