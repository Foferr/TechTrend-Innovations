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
}
