package org.acme.DTO.EventLogDTOs;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class EventLogPostDTO {

    @Schema(example = "2")
    private String userId;

    @Schema(example = "button")
    private String eventTarget;

    @Schema(example = "Your metadata here")
    private String metadata;

}
