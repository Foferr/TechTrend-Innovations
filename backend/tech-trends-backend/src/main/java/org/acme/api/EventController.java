package org.acme.api;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.DTO.EventLogDTOs.EventLogPostDTO;
import org.acme.DTO.EventLogDTOs.EventLogPutDTO;
import org.acme.DTO.FaqDTOs.FaqUpdateRequestDTO;
import org.acme.model.Event;
import org.acme.model.User;
import org.acme.service.EventService;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Path("/eventLog")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EventController {

    @Inject
    EventService eventService;

    @GET
    @PermitAll
    public Response getAllEvents() {
        try {
            List<Event> events = eventService.getAllEvents();
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
    @Path("byUserId/{userId}")
    public Response getEventsByUserId(@PathParam("userId") Long userId) {
        try {
            List<Event> events = eventService.getEventsByUserId(userId);
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
    @Path("byTarget/{eventTarget}")
    public Response getEventsByEventTarget(@PathParam("eventTarget") String eventTarget) {
        try {
            List<Event> events = eventService.getEventsByEventTarget(eventTarget);
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
    @Path("byDate/{eventDate}")
    public Response getEventsByDate(@PathParam("eventDate") LocalDate eventDate) {
        try {
            List<Event> events = eventService.getEventsByEventDate(eventDate);
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
    @RequestBody( content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = EventLogPostDTO.class)
    ))
    public Response createEvent(Event event) {
        try {
            // Assuming userService.createUser fetches the user from the database first
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
    @Path("/{eventLogId}")
    @RequestBody( content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = EventLogPutDTO.class)
    ))
    public Response updateUser(@PathParam("eventLogId") Long eventLogId, Event event) {
        try {
            Event existingEvent = eventService.getEventById(eventLogId);
            if (existingEvent == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"Event not found\"}")
                        .build();
            }

            // Update the existing user with the new user data
            existingEvent.setUserId(event.getUserId());
            existingEvent.setEventTarget(event.getEventTarget());
            existingEvent.setCreatedAt(event.getCreatedAt());
            existingEvent.setMetadata(event.getMetadata());


            eventService.updateEvent(existingEvent);

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
