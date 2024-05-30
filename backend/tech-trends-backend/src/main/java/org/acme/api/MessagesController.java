package org.acme.api;



import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.DTO.FaqDTOs.FaqUpdateRequestDTO;
import org.acme.DTO.MessageDTOs.MessagePostDTO;
import org.acme.model.Messages;
import org.acme.service.MessagesService;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

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
    @RequestBody( content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = MessagePostDTO.class)
    ))
    public Response postMessage(Messages messages) {
        try {
            messagesService.postMessage(messages);

            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"Message posted\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/byUserId/{userId}")
    public List<Messages> getMessagesByUserId(@PathParam("userId") String userId) {
        return messagesService.getMessagesByUserId(userId);
    }

    @GET
    @Path("/byChatHistoryIdAndUserId/{userId}/{chatId}")
    public List<Messages> getMessagesByUserIdChatId(@PathParam("userId") String userId, @PathParam("chatId") String chatId) {
        return messagesService.getMessagesByUserIdChatId(userId, chatId);
    }

    @GET
    @Path("/byChatId/{chatId}")
    public List<Messages> getMessagesByChatId(@PathParam("chatId") String chatId) {
        return messagesService.getMessagesByChatId(chatId);
    }

    @GET
    @Path("/bySenderType/{senderType}")
    public List<Messages> getMessagesBySenderType(@PathParam("senderType") String senderType) {
        return messagesService.getMessagesBySenderType(senderType);
    }
}
