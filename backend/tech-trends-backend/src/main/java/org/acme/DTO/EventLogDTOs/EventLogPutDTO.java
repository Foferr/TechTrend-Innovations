package org.acme.DTO.EventLogDTOs;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class EventLogPutDTO {

    @Schema(example = "2")
    private Long userId;

    @Schema(example = "button")
    private String eventTarget;

    @Schema(example = "Your metadata here")
    private String metadata;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEventTarget() {
        return eventTarget;
    }

    public void setEventTarget(String eventTarget) {
        this.eventTarget = eventTarget;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}
