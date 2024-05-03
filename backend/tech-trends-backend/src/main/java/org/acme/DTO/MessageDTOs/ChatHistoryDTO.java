package org.acme.DTO.MessageDTOs;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class ChatHistoryDTO {
    @Schema(example = "1")
    private Long id;
}
