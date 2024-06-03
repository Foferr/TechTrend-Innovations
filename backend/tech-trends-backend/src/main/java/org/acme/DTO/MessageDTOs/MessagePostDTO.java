package org.acme.DTO.MessageDTOs;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class MessagePostDTO {
    @Schema(example = "user/bot")
    private String senderType;

    @Schema(example = "Your message content here")
    private String messageContent;

    public String getSenderType() {
        return senderType;
    }

    public void setSenderType(String senderType) {
        this.senderType = senderType;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
}
