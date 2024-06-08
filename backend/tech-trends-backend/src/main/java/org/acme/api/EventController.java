package org.acme.api;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.DTO.EventLogDTOs.EventLogPostDTO;
import org.acme.DTO.EventLogDTOs.EventLogPutDTO;
import org.acme.DTO.FaqDTOs.FaqUpdateRequestDTO;
import org.acme.DTO.decryptionDTOs.DecryptedEventDTO;
import org.acme.model.Event;
import org.acme.model.User;
import org.acme.service.EventService;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Path("/eventLog")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EventController {

    @Inject
    EventService eventService;

    @GET
    //@RolesAllowed("admin")
    @PermitAll
    public Response getAllEvents() {
        try {
            List<DecryptedEventDTO> events = eventService.getAllEvents();
            if (events.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"No events found\"}")
                        .build();
            }
            return Response.ok(events).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    //@RolesAllowed("admin")
    @PermitAll
    @Path("byUserId/{userId}")
    public Response getEventsByUserId(@PathParam("userId") Long userId) {
        try {
            List<DecryptedEventDTO> events = eventService.getEventsByUserId(userId);
            if (events.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"No events found with provided user id\"}")
                        .build();
            }
            return Response.ok(events).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(String.format("{\"error\": \"Failed to get events: %s\"}", e.getMessage()))
                    .build();
        }
    }

    @GET
    //@RolesAllowed("admin")
    @PermitAll
    @Path("byTarget/{eventTarget}")
    public Response getEventsByEventTarget(@PathParam("eventTarget") String eventTarget) {
        try {
            List<DecryptedEventDTO> events = eventService.getEventsByEventTarget(eventTarget);
            if (events.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"No events found with provided target\"}")
                        .build();
            }
            return Response.ok(events).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(String.format("{\"error\": \"Failed to get events: %s\"}", e.getMessage()))
                    .build();
        }
    }

    @GET
    //@RolesAllowed("admin")
    @PermitAll
    @Path("byDate/{eventDate}")
    public Response getEventsByDate(@PathParam("eventDate") LocalDate eventDate) {
        try {
            List<DecryptedEventDTO> events = eventService.getEventsByEventDate(eventDate);
            if (events.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"No events found with provided date\"}")
                        .build();
            }
            return Response.ok(events).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(String.format("{\"error\": \"Failed to get events by date: %s\"}", e.getMessage()))
                    .build();
        }
    }

    @POST
    //@RolesAllowed("admin")
    @PermitAll
    @RequestBody( content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = EventLogPostDTO.class)
    ))
    public Response createEvent(Event event) {
        try {
            eventService.createEvent(event);

            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"Event created successfully\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @DELETE
    //@RolesAllowed("admin")
    @PermitAll
    public Response deleteEvents() {
        try {
            eventService.deleteAllEvents();

            return Response.status(Response.Status.ACCEPTED)
                    .entity("{\"message\": \"All events deleted successfully\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @PUT
    //@RolesAllowed("admin")
    @PermitAll
    @Path("/{eventLogId}")
    @RequestBody( content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = EventLogPutDTO.class)
    ))
    public Response updateUser(@PathParam("eventLogId") Long eventLogId, Event event) {
        try {
            Optional<DecryptedEventDTO> existingEventOptional = eventService.getEventById(eventLogId);
            if (existingEventOptional.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"Event not found\"}")
                        .build();
            }

            eventService.updateEvent(eventLogId, event);

            return Response.status(Response.Status.OK)
                    .entity("{\"message\": \"Event updated successfully\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @DELETE
    //@RolesAllowed("admin")
    @PermitAll
    @Path("/{eventLogId}")
    public Response deleteEventById(@PathParam("eventLogId") Long id) {
        try {
            eventService.deleteEventById(id);

            return Response.status(Response.Status.OK)
                    .entity("{\"message\": \"Event deleted successfully\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
}
