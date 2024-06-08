package org.acme.api;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.acme.DTO.CompanyNewsDTOs.CompanyNewsPostDTO;
import org.acme.DTO.FaqDTOs.FaqUpdateRequestDTO;
import org.acme.DTO.decryptionDTOs.DecryptedCompanyNewsDTO;
import org.acme.exceptions.UserNotFoundException;
import org.acme.model.ChatHistory;
import org.acme.model.CompanyNews;
import org.acme.service.ChatHistoryService;
import org.acme.service.CompanyNewsService;
import org.acme.service.FaqService;
import org.acme.service.UserService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import java.util.List;
import java.util.Optional;

@Path("/companyNews")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CompanyNewsController {

    @Inject
    CompanyNewsService companyNewsService;

    @Inject
    UserService userService;

    @GET
    //@RolesAllowed({"admin", "base_user"})
    @PermitAll
    @Path("/getAll")
    public Response getAllCompanyNews() {
        try {
            List<DecryptedCompanyNewsDTO> companyNews = companyNewsService.getAllCompanyNews();
            if (companyNews.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"No news found\"}")
                        .build();
            }
            return Response.ok(companyNews).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @PermitAll
    //@RolesAllowed({"admin", "base_user"})
    @Path("/byId/{companyNewsId}")
    public Response getCompanyNewsByNewsId(@PathParam("companyNewsId") Long companyNewsId) {
        try {
            Optional<DecryptedCompanyNewsDTO> companyNews = companyNewsService.getCompanyNewsById(companyNewsId);
            if(companyNews.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\":\"No Company News found for News ID " + companyNewsId + "\"}")
                        .build();
            }
            return Response.ok(companyNews.get()).build();

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
            List<DecryptedCompanyNewsDTO> companyNews = companyNewsService.getCompanyNewsByUserId(userId);
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
            List<DecryptedCompanyNewsDTO> companyNews = companyNewsService.getCompanyNewsByStatus(status);
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
    public Response postInsertCompanyNews(@PathParam("adminId") Long adminId, CompanyNews companyNews) {
        try {
            if (!userService.isAdminValid(adminId)) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("{\"error\": \"Invalid admin ID\"}")
                        .build();
            }

            companyNewsService.createCompanyNews(adminId, companyNews);
            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"FAQ created successfully\"}")
                    .build();
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"User not found\", \"details\": \"" + e.getMessage() + "\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Failed to create FAQ\", \"details\": \"" + e.getMessage() + "\"}")
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
    //@RolesAllowed("admin")
    @PermitAll
    @Path("/{adminId}/{companyNewsId}")
    @Operation(summary = "Update company news", description = "Update a news piece")
    @RequestBody( content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = CompanyNewsPostDTO.class)
    ))
    public Response updateCompanyNews(@PathParam("adminId") Long adminId, @PathParam("companyNewsId") Long companyNewsId, CompanyNews companyNews) {
        try {
            if (!userService.isAdminValid(adminId)) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("{\"error\": \"Invalid admin ID\"}")
                        .build();
            }

            companyNewsService.updateCompanyNews(adminId, companyNewsId, companyNews);
            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"Company news updated successfully\"}")
                    .build();
        } catch (CompanyNewsService.CompanyNewsNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"News not found\", \"details\": \"" + e.getMessage() + "\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Failed to update news\", \"details\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
}
