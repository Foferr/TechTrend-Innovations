package org.acme.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.DTO.FaqDTOs.FaqUpdateRequestDTO;
import org.acme.DTO.decryptionDTOs.DecryptedFAQDTO;
import org.acme.exceptions.UserNotFoundException;
import org.acme.model.Event;
import org.acme.model.Faq;
import org.acme.model.User;
import org.acme.service.FaqService;
import org.acme.utils.EncryptionUtil;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;
import java.util.Optional;


@Path("/FAQ")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FaqController {

    @Inject
    FaqService faqService;

    // Endpoint para obtener todas las FAQs.


    @GET
    //@RolesAllowed({"admin", "base_user"})
    @PermitAll
    @Path("/getAll")
    public Response getAllFaqs() {
        try {
            List<DecryptedFAQDTO> faqs = faqService.getAllFaqs();
            if (faqs.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"No events found\"}")
                        .build();
            }
            return Response.ok(faqs).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    // Endpoint para obtener una FAQ por su ID.
    @GET
    //@RolesAllowed({"admin", "base_user"})
    @PermitAll
    @Path("/{faqId}")
    public Response getFaqById(@PathParam("faqId") Long faqId) {
        Optional<DecryptedFAQDTO> faqDTO = faqService.getFaqById(faqId);
        if (faqDTO.isPresent()) {
            return Response.ok(faqDTO.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"FAQ not found\"}")
                    .build();
        }
    }

    // Endpoint para actualizar una FAQ dado su ID y un ID de admin válido.
    @PUT
    //@RolesAllowed("admin")
    @PermitAll
    @Path("/{adminId}/{faqId}")
    @Operation(summary = "Update FAQ", description = "Update an FAQ entry")
    @RequestBody( content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = FaqUpdateRequestDTO.class)
    ))
    public Response updateFaq(@PathParam("adminId") Long adminId, @PathParam("faqId") Long faqId, Faq faq) {
        try {
            if (!faqService.isAdminValid(adminId)) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("{\"error\": \"Invalid admin ID\"}")
                        .build();
            }

            faqService.updateFaq(faqId, faq);
            return Response.status(Response.Status.OK)
                    .entity("{\"message\": \"FAQ updated successfully\"}")
                    .build();
        } catch (FaqService.FaqNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Failed to update FAQ\"}")
                    .build();
        }
    }


    // Endpoint para borrar una FAQ dado su ID y un ID de admin válido.
    @DELETE
    //@RolesAllowed("admin")
    @PermitAll
    @Path("/{adminId}/{faqId}")
    public Response deleteFaq(@PathParam("adminId") Long adminId, @PathParam("faqId") Long faqId) {
        try {
            if (!faqService.isAdminValid(adminId)) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("{\"error\": \"Invalid admin ID\"}")
                        .build();
            }
            boolean deleted = faqService.deleteFaq(faqId);
            if (!deleted) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"FAQ not found or cannot be deleted\"}")
                        .build();
            }

            return Response.status(Response.Status.OK)
                    .entity("{\"message\": \"FAQ deleted successfully\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Failed to delete FAQ: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    // Endpoint para obtener todas las FAQs creadas por un admin específico.

    @GET
    //@RolesAllowed("admin")
    @PermitAll
    @Path("/getByAdminId/{adminId}")
    public Response getFaqsByAdmin(@PathParam("adminId") Long adminId) {
        try {
            List<DecryptedFAQDTO> faqs = faqService.getFaqsByAdminId(adminId);
            if (faqs.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"No faqs found with provided admin id\"}")
                        .build();
            }
            return Response.ok(faqs).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(String.format("{\"error\": \"Failed to get faqs: %s\"}", e.getMessage()))
                    .build();
        }
    }

    // Endpoint para crear una nueva FAQ dado un ID de admin válido.
    @POST
    @PermitAll
    //@RolesAllowed("admin")
    @Path("/{adminId}")
    @RequestBody( content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = FaqUpdateRequestDTO.class)
    ))
    public Response createFaq(@PathParam("adminId") Long adminId, Faq faq) {
        try {
            if (!faqService.isAdminValid(adminId)) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("{\"error\": \"Invalid admin ID\"}")
                        .build();
            }

            faqService.createFaq(adminId, faq);
            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"FAQ created successfully\"}")
                    .build();
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"User not found\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Failed to create FAQ\"}")
                    .build();
        }
    }

    //    // Endpoint para obtener FAQs por su estado.
    @GET
    @PermitAll
    //@RolesAllowed({"admin", "base_user"})
    @Path("/status/{status}")
    public Response getFaqsByStatus(@PathParam("status") String status) {
        try {
            String encryptedStatus = EncryptionUtil.encrypt(status);
            List<DecryptedFAQDTO> faqs = faqService.getFaqsByStatus(encryptedStatus);
            if (faqs.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"No faqs found with provided status\"}")
                        .build();
            }
            return Response.ok(faqs).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(String.format("{\"error\": \"Failed to get faqs: %s\"}", e.getMessage()))
                    .build();
        }
    }
}
