package org.acme.api;



import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.DTO.FaqDTOs.FaqUpdateRequestDTO;
import org.acme.DTO.MessageDTOs.MessagePostDTO;
import org.acme.DTO.decryptionDTOs.DecryptedMessageDTO;
import org.acme.model.ChatHistory;
import org.acme.model.Faq;
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
    public Response getAllMessages() {
        try {
            List<DecryptedMessageDTO> messageDTOS = messagesService.getAllMessages();
            if (messageDTOS.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"No messages found\"}")
                        .build();
            }
            return Response.ok(messageDTOS).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @POST
    //@RolesAllowed({"admin", "base_user"})
    @PermitAll
    @Path("/postMessage/{chatHistoryId}")
    @RequestBody( content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = MessagePostDTO.class)
    ))
    public Response postMessage(@PathParam("chatHistoryId") Long chatHistoryId, MessagePostDTO messagePostDTO) {
        try {
            Optional<ChatHistory> chatHistory = chatHistoryService.chatHistoryExists(chatHistoryId);
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
    public Response getMessagesByUserId(@PathParam("userId") Long userId) {
        try {
            List<DecryptedMessageDTO> messages = messagesService.getMessagesByUserId(userId);
            if (messages.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"No messages found with provided user id\"}")
                        .build();
            }
            return Response.ok(messages).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(String.format("{\"error\": \"Failed to get messages: %s\"}", e.getMessage()))
                    .build();
        }
    }

    @GET
    //@RolesAllowed({"admin", "base_user"})
    @PermitAll
    @Path("/byChatHistoryIdAndUserId/{userId}/{chatId}")
    public Response getMessagesByUserIdChatId(@PathParam("userId") Long userId, @PathParam("chatId") Long chatId) {
        try {
            List<DecryptedMessageDTO> messages = messagesService.getMessagesByUserIdChatId(userId, chatId);
            if (messages.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"No messages found with provided chat history id and user id\"}")
                        .build();
            }
            return Response.ok(messages).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(String.format("{\"error\": \"Failed to get messages: %s\"}", e.getMessage()))
                    .build();
        }
    }

    @GET
    //@RolesAllowed({"admin", "base_user"})
    @PermitAll
    @Path("/byChatId/{chatId}")
    public Response getMessagesByChatId(@PathParam("chatId") Long chatId) {
        try {
            List<DecryptedMessageDTO> messages = messagesService.getMessagesByChatId(chatId);
            if (messages.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"No messages found with provided chat history id\"}")
                        .build();
            }
            return Response.ok(messages).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(String.format("{\"error\": \"Failed to get messages: %s\"}", e.getMessage()))
                    .build();
        }
    }

    @GET
    //@RolesAllowed({"admin", "base_user"})
    @PermitAll
    @Path("/bySenderType/{senderType}")
    public Response getMessagesBySenderType(@PathParam("senderType") String senderType) {
        try {
            List<DecryptedMessageDTO> messages = messagesService.getMessagesBySenderType(senderType);
            if (messages.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"No messages found with provided sender type\"}")
                        .build();
            }
            return Response.ok(messages).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(String.format("{\"error\": \"Failed to get messages: %s\"}", e.getMessage()))
                    .build();
        }
    }
}
