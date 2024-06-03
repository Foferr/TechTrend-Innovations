package org.acme.DTO.ChatHistoryDTOs;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class ChatHistoryPostRequestDTO {
    @Schema(example = "active/frozen")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Schema(example = "Title of the chat")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
