package org.acme.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.model.ChatHistory;
import org.acme.service.ChatHistoryService;

import java.util.List;

@Path("/chathistory")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ChatHistoryController {

    @Inject
    ChatHistoryService chathistoryService;

    @GET
    public List<ChatHistory> getAllChatHistory() {
        return chathistoryService.getAllChatHistory();
    }

    @POST
    @Path("/postChatHistory")
    public Response postChatHistory(ChatHistory chatHistory) {
        try {
            chathistoryService.postChatHistory(chatHistory);

            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"Chat created\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

}
