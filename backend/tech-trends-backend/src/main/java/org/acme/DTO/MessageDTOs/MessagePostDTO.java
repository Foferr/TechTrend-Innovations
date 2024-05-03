package org.acme.DTO.MessageDTOs;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class MessagePostDTO {
    private ChatHistoryDTO chatHistory;

    @Schema(example = "user/bot")
    private String senderType;

    @Schema(example = "Your message content here")
    private String messageContent;
}
