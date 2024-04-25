package org.acme.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.acme.model.ChatHistory;
import org.acme.model.CompanyNews;
import org.acme.service.ChatHistoryService;
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
                        .entity("{\"message\":\"No Company News found for News ID " + newsId + "\"}")
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
    /* 
    //Implementar endpoint /companyNews/{adminId}/createNews (POST)
    TEST 
    adminId=2 o 652
    Body:
    {
        "companynews_id": 0,
        "title": "titulo noticia 2",
        "news_content": "contenido noticia 2",
        "status": "preliminar"
    }
    */
    @POST
    @Path("/companyNews5/{adminId}")
    public Response postInsertCompanyNews(@PathParam("adminId") Long adminId, CompanyNews createNews) {
        try {
            companyNewsService.postInsertCompanyNews(adminId, createNews);

            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"News created\"}")
                    .build();
        }  catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
   

    //Implementar endpoint /companyNews/delete/{companyNewsId}
    @DELETE
    @Path("/companyNews6/{companyNewsId}")
    public Response postDeleteCompanyNews(@PathParam("companyNewsId") Long companyNewsId) {
        try {
            boolean deleted = companyNewsService.postDeleteCompanyNews(companyNewsId);
            if (deleted) {
                return Response.noContent().build(); // Return 204 No Content on successful deletion
            }
            // Return 404 Not Found if there is no entity to delete
            return Response.status(Response.Status.NOT_FOUND).entity("{\"message\":\"No Company News found with ID " + companyNewsId + "\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}")
                    .build();
        }
    }

     //Implementar endpoint /companyNews/{adminId}/{companyNewsId} (PUT)

    @PUT
    @Path("/companyNews7/{adminId}/{companyNewsId}")
    public Response updateCompanyNews(@PathParam("adminId") Long adminId,@PathParam("companyNewsId") Long companyNewsId, @QueryParam("status") String status) {
        try {
            Optional<CompanyNews> updateCompanyNews = companyNewsService.updateCompanyNews(adminId,companyNewsId, status);
            if (updateCompanyNews.isPresent()) {
                return Response.ok(updateCompanyNews.get()).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\":\"No Company News found with ID " + companyNewsId + "\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}")
                    .build();
        }
    }
}
