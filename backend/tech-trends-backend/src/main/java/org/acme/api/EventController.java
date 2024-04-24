package org.acme.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.model.Event;
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

//    @DELETE
//    @Path("/{id}")
//    public Response deleteUser(@PathParam("id") Long id) {
//        userService.deleteUser(id);
//        return Response.status(Response.Status.NO_CONTENT).build();
//    }
}
