package org.acme.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.model.Event;
import org.acme.model.User;
import org.acme.service.EventService;

import java.time.LocalDate;
import java.util.List;

@Path("/eventLog")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EventController {

    @Inject
    EventService eventService;

    @GET
    @Path("/getAll")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GET
    @Path("/{userId}")
    public List<Event> getEventsByUserId(@PathParam("userId") Long userId) { return eventService.getEventsByUserId(userId); }

    @GET
    @Path("/{eventTarget}")
    public List<Event> getEventsByEventTarget(@PathParam("eventTarget") String eventTarget) { return eventService.getEventsByEventTarget(eventTarget); }


    @GET
    @Path("/{eventDate}")
    public List<Event> getEventsByDate(@PathParam("eventDate") LocalDate eventDate) { return eventService.getEventsByEventDate(eventDate); }

    @POST
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
    public Response updateUser(@PathParam("eventLogId") Long id, Event event) {
        try {
            Event existingEvent = eventService.getEventById(id);
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
