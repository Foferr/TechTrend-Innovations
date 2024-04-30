//package org.acme.api;
//
//import jakarta.inject.Inject;
//import jakarta.ws.rs.*;
//import jakarta.ws.rs.core.MediaType;
//import jakarta.ws.rs.core.Response;
//import org.acme.model.Faq;
//import org.acme.service.FaqService;
//
//import java.util.List;
//
//
//@Path("/FAQ")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)
//public class FaqController {
//
//    @Inject
//    FaqService faqService;
//
//    // Endpoint para obtener todas las FAQs.
//    @GET
//    @Path("/getAll")
//    public List<Faq> getAllFaqs() {
//        return faqService.getAllFaqs();
//    }
//
//    // Endpoint para obtener una FAQ por su ID.
//    @GET
//    @Path("/{faqId}")
//    public Faq getFaqById(@PathParam("faqId") String faqId) {
//        return faqService.getFaqById(faqId);
//    }
//
//    // Endpoint para actualizar una FAQ dado su ID y un ID de admin válido.
//    @PUT
//    @Path("/{adminId}/{faqId}")
//    public Response updateFaq(@PathParam("adminId") String adminId, @PathParam("faqId") String faqId, Faq faq) {
//        if (!faqService.isAdminValid(adminId)) {
//            return Response.status(Response.Status.FORBIDDEN).entity("{\"error\": \"Invalid admin ID\"}").build();
//        }
//
//        if (!faqService.isFaqValid(faqId)) {
//            return Response.status(Response.Status.NOT_FOUND).entity("{\"error\": \"FAQ not found\"}").build();
//        }
//
//        faqService.updateFaq(adminId, faqId, faq);
//        return Response.status(Response.Status.OK)
//                .entity("{\"message\": \"FAQ updated successfully\"}")
//                .build();
//    }
//
//    // Endpoint para borrar una FAQ dado su ID y un ID de admin válido.
//    @DELETE
//    @Path("/{adminId}/{faqId}")
//    public Response deleteFaq(@PathParam("adminId") String adminId, @PathParam("faqId") String faqId) {
//        if (!faqService.isAdminValid(adminId)) {
//            return Response.status(Response.Status.FORBIDDEN).entity("{\"error\": \"Invalid admin ID\"}").build();
//        }
//
//        if (!faqService.deleteFaq(adminId, faqId)) {
//            return Response.status(Response.Status.NOT_FOUND).entity("{\"error\": \"FAQ not found or cannot be deleted\"}").build();
//        }
//
//        return Response.status(Response.Status.OK)
//                .entity("{\"message\": \"FAQ deleted successfully\"}")
//                .build();
//    }
//
//    // Endpoint para obtener todas las FAQs creadas por un admin específico.
//    @GET
//    @Path("/{adminId}")
//    public List<Faq> getFaqsByAdmin(@PathParam("adminId") String adminId) {
//        return faqService.getFaqsByAdmin(adminId);
//    }
//
//    // Endpoint para crear una nueva FAQ dado un ID de admin válido.
//    @POST
//    @Path("/{adminId}")
//    public Response createFaq(@PathParam("adminId") String adminId, Faq faq) {
//        if (!faqService.isAdminValid(adminId)) {
//            return Response.status(Response.Status.FORBIDDEN).entity("{\"error\": \"Invalid admin ID\"}").build();
//        }
//
//        faqService.createFaq(adminId, faq);
//        return Response.status(Response.Status.CREATED)
//                .entity("{\"message\": \"FAQ created successfully\"}")
//                .build();
//    }
//
//    // Endpoint para obtener FAQs por su estado.
//    @GET
//    @Path("/status/{status}")
//    public List<Faq> getFaqsByStatus(@PathParam("status") String status) {
//        return faqService.getFaqsByStatus(status);
//    }
//}
