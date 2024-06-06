package org.acme.api;

import java.util.List;
import java.util.Optional;

import org.acme.DTO.CompanyNewsDTOs.CompanyNewsPostDTO;
import org.acme.model.CompanyNews;
import org.acme.service.CompanyNewsService;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/companyNews")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CompanyNewsController {

    @Inject
    CompanyNewsService companyNewsService;
    
    @GET
    //@RolesAllowed({"admin", "base_user"})
    @PermitAll
    @Path("/getAll")
    public List<CompanyNews> getAllCompanyNews() {
        return companyNewsService.getAllCompanyNews();
    }
     
    @GET
    @PermitAll
    //@RolesAllowed({"admin", "base_user"})
    @Path("/byId/{companyNewsId}")
    public Response getCompanyNewsByNewsId(@PathParam("companyNewsId") Long companyNewsId) {
        try {
            CompanyNews companyNews = companyNewsService.getCompanyNewsById(companyNewsId);
            if(companyNews == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\":\"No Company News found for News ID " + companyNewsId + "\"}")
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
    @PermitAll
    //@RolesAllowed("admin")
    @Path("/byAdminId/{adminId}")
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
    //@RolesAllowed({"admin", "base_user"})
    @PermitAll
    @Path("/byStatus/{status}")
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
    //@RolesAllowed("admin")
    @PermitAll
    @Path("/{adminId}")
    @RequestBody( content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = CompanyNewsPostDTO.class)
    ))
    public Response postInsertCompanyNews(@PathParam("adminId") Long adminId, CompanyNews createNews) {
        try {
            companyNewsService.postInsertCompanyNews(adminId, createNews);

            return Response.ok(createNews).build();
            //return Response.status(Response.Status.OK)
            //        .entity("{\"message\": \"News created\"}")
            //        .build();

        }  catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
   

    //Implementar endpoint /companyNews/delete/{companyNewsId}
    @DELETE
    //@RolesAllowed("admin")
    @PermitAll
    @Path("/{companyNewsId}")
    public Response postDeleteCompanyNews(@PathParam("companyNewsId") Long companyNewsId) {
        try {
            boolean deleted = companyNewsService.postDeleteCompanyNews(companyNewsId);
            if (deleted) {
                return Response.ok().build(); // Return 204 No Content on successful deletion
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
    //@RolesAllowed("admin")
    @PermitAll
    @Path("/{adminId}/{companyNewsId}")
    @RequestBody( content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = CompanyNewsPostDTO.class)
    ))
    public Response updateCompanyNews(@PathParam("adminId") Long adminId,@PathParam("companyNewsId") Long companyNewsId,CompanyNews updateNews ) {
        try {
            Optional<CompanyNews> updateCompanyNews = companyNewsService.updateCompanyNews(adminId,companyNewsId, updateNews);
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
