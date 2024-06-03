package org.acme.api;



import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.DTO.FaqDTOs.FaqUpdateRequestDTO;
import org.acme.DTO.MessageDTOs.MessagePostDTO;
import org.acme.model.ChatHistory;
import org.acme.model.Messages;
import org.acme.service.ChatHistoryService;
import org.acme.service.MessagesService;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import java.util.List;
import java.util.Optional;

@Path("/messages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MessagesController {

    @Inject
    MessagesService messagesService;

    @Inject
    ChatHistoryService chatHistoryService;

    @GET
    @PermitAll
    //@RolesAllowed({"admin", "base_user"})
    public List<Messages> getAllMessages() {
        return messagesService.getAllMessages();
    }

    @POST
    //@RolesAllowed({"admin", "base_user"})
    @PermitAll
    @Path("/postMessage")
    @RequestBody( content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = MessagePostDTO.class)
    ))
    public Response postMessage(@QueryParam("chatHistoryId") Long chatHistoryId, MessagePostDTO messagePostDTO) {
        try {
            Optional<ChatHistory> chatHistory = chatHistoryService.getChatHistoryById(chatHistoryId);
            if(chatHistory.isPresent()) {
                Messages message = new Messages(chatHistory.get(), messagePostDTO.getSenderType(), messagePostDTO.getMessageContent());
                messagesService.postMessage(message);
                return Response.status(Response.Status.CREATED)
                        .entity("{\"message\": \"Message posted\"}")
                        .build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Provided chat history id is not found\"}")
                        .build();
            }


        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    //@RolesAllowed({"admin", "base_user"})
    @PermitAll
    @Path("/byUserId/{userId}")
    public List<Messages> getMessagesByUserId(@PathParam("userId") String userId) {
        return messagesService.getMessagesByUserId(userId);
    }

    @GET
    //@RolesAllowed({"admin", "base_user"})
    @PermitAll
    @Path("/byChatHistoryIdAndUserId/{userId}/{chatId}")
    public List<Messages> getMessagesByUserIdChatId(@PathParam("userId") String userId, @PathParam("chatId") String chatId) {
        return messagesService.getMessagesByUserIdChatId(userId, chatId);
    }

    @GET
    //@RolesAllowed({"admin", "base_user"})
    @PermitAll
    @Path("/byChatId/{chatId}")
    public List<Messages> getMessagesByChatId(@PathParam("chatId") String chatId) {
        return messagesService.getMessagesByChatId(chatId);
    }

    @GET
    //@RolesAllowed({"admin", "base_user"})
    @PermitAll
    @Path("/bySenderType/{senderType}")
    public List<Messages> getMessagesBySenderType(@PathParam("senderType") String senderType) {
        return messagesService.getMessagesBySenderType(senderType);
    }
}
