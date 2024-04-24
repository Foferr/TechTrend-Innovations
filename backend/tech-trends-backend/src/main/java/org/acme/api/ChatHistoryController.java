package org.acme.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.model.ChatHistory;
import org.acme.service.ChatHistoryService;

import java.util.List;
import java.util.Optional;

@Path("/chatHistory")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ChatHistoryController {

    @Inject
    ChatHistoryService chathistoryService;

    @GET
    public List<ChatHistory> getAllChatHistories() {
        return chathistoryService.getAllChatHistory();
    }

    @GET
    @Path("/user/{userId}")
    public Response getChatHistoryByUserId(@PathParam("userId") Long userId) {
        try {
            List<ChatHistory> chatHistories = chathistoryService.getChatHistoryByUserId(userId);
            if(chatHistories.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\":\"No chat history found for user ID " + userId + "\"}")
                        .build();
            }
            return Response.ok(chatHistories).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @POST
    @Path("/user/{userId}")
    public Response postChatHistory(@PathParam("userId") Long userId, ChatHistory chatHistory) {
        try {
            chathistoryService.postChatHistory(chatHistory, userId);

            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"Chat created\"}")
                    .build();
        } catch (ChatHistoryService.UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/{chatHistoryId}")
    public Response getChatHistoryById(@PathParam("chatHistoryId") Long chatHistoryId) {
        Optional<ChatHistory> chatHistory = chathistoryService.getChatHistoryById(chatHistoryId);
        if (chatHistory.isPresent()) {
            return Response.ok(chatHistory.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\":\"No chat history found with ID " + chatHistoryId + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/status/{userId}/{status}")
    public Response getChatHistoryByUserIdAndStatus(@PathParam("userId") Long userId, @PathParam("status") String status) {
        try {
            List<ChatHistory> chatHistories = chathistoryService.getChatHistoriesByUserIdAndStatus(userId, status);
            if(chatHistories.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\":\"No chat history found for user ID " + userId + " or with status " + status +"\"}")
                        .build();
            }
            return Response.ok(chatHistories).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @PUT
    @Path("/{chatHistoryId}")
    public Response updateChatHistory(@PathParam("chatHistoryId") Long chatHistoryId, @QueryParam("status") String status) {
        try {
            Optional<ChatHistory> updatedChatHistory = chathistoryService.updateChatHistory(chatHistoryId, status);
            if (updatedChatHistory.isPresent()) {
                return Response.ok(updatedChatHistory.get()).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\":\"No chat history found with ID " + chatHistoryId + "\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @DELETE
    @Path("/{chatHistoryId}")
    public Response deleteChatHistory(@PathParam("chatHistoryId") Long chatHistoryId) {
        try {
            boolean deleted = chathistoryService.deleteChatHistory(chatHistoryId);
            if (deleted) {
                return Response.noContent().build(); // Return 204 No Content on successful deletion
            }
            // Return 404 Not Found if there is no entity to delete
            return Response.status(Response.Status.NOT_FOUND).entity("{\"message\":\"No chat history found with ID " + chatHistoryId + "\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}")
                    .build();
        }
    }

}