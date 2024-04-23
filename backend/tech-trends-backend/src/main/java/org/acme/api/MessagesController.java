package org.acme.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.model.Messages;
import org.acme.service.MessagesService;

import java.util.List;

@Path("/messages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MessagesController {

    @Inject
    MessagesService messagesService;

    @GET
    public List<Messages> getAllMessages() {
        return messagesService.getAllMessages();
    }

    @POST
    @Path("/postMessage")
    public Response postMessage(Messages message) {
        try {
            messagesService.postMessage(message);

            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"Message posted\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

}
