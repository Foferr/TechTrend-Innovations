package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Event;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class EventRepository implements PanacheRepository<Event> {
    //Panache repository implements the basic functionality
    // Custom database queries go here

    public List<Event> getEventsByUserId(long userId) {
        return list("userId", userId);
    }

    public List<Event> getEventsByEventTarget(String eventTarget) {
        return list("eventTarget", eventTarget);
    }

    public List<Event> getEventsByEventDate(LocalDate eventDate) {
        return list("createdAt", eventDate);
    }

}
