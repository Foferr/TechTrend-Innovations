package org.acme.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.DTO.FaqDTOs.FaqUpdateRequestDTO;
import org.acme.model.Event;
import org.acme.model.Faq;
import org.acme.model.User;
import org.acme.service.FaqService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import java.util.List;


@Path("/FAQ")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FaqController {

    @Inject
    FaqService faqService;

    // Endpoint para obtener todas las FAQs.


    @GET
    @Path("/getAll")
    public Response getAllFaqs() {
        try {
            List<Faq> faqs = faqService.getAllFaqs();
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
    @Path("/{faqId}")
    public Response getFaqById(@PathParam("faqId") Long faqId) {
        Faq faq = faqService.getFaqById(faqId);
        if (faq != null) {
            return Response.ok(faq).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Faq not found\"}")
                    .build();
        }
    }

    // Endpoint para actualizar una FAQ dado su ID y un ID de admin válido.
    @PUT
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
    @Path("/getByAdminId/{adminId}")
    public Response getFaqsByAdmin(@PathParam("adminId") Long adminId) {
        try {
            List<Faq> faqs = faqService.getFaqsByAdminId(adminId);
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
        } catch (FaqService.UserNotFoundException e) {
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
    @Path("/status/{status}")
    public Response getFaqsByStatus(@PathParam("status") String status) {
        try {
            List<Faq> faqs = faqService.getFaqsByStatus(status);
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
