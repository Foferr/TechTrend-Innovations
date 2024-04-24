package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.model.Event;
import org.acme.repository.EventRepository;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class EventService {

    @Inject
    EventRepository eventRepository;

    public List<Event> getAllEvents() { return eventRepository.listAll(); }

    public List<Event> getEventsByUserId(long userId) { return eventRepository.getEventsByUserId(userId); }

    public List<Event> getEventsByEventTarget(String eventTarget) { return eventRepository.getEventsByEventTarget(eventTarget); }

    public List<Event> getEventsByEventDate(LocalDate eventDate) { return eventRepository.getEventsByEventDate(eventDate); }
}
