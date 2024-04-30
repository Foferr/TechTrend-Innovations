//package org.acme.service;
//
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.inject.Inject;
//import jakarta.transaction.Transactional;
//import org.acme.model.Event;
//import org.acme.model.User;
//import org.acme.repository.EventRepository;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@ApplicationScoped
//public class EventService {
//
//    @Inject
//    EventRepository eventRepository;
//
//    @Transactional
//    public void createEvent(Event event) {
//        eventRepository.persist(event);
//    }
//
//    @Transactional
//    public void deleteAllEvents() { eventRepository.deleteAll(); }
//
//    @Transactional
//    public void deleteEventById(Long id) { eventRepository.deleteById(id); }
//
//    public Event getEventById(Long eventId) {
//        return eventRepository.findById(eventId);
//    }
//
//    public List<Event> getAllEvents() { return eventRepository.listAll(); }
//
//    public List<Event> getEventsByUserId(long userId) { return eventRepository.getEventsByUserId(userId); }
//
//    public List<Event> getEventsByEventTarget(String eventTarget) { return eventRepository.getEventsByEventTarget(eventTarget); }
//
//    public List<Event> getEventsByEventDate(LocalDate eventDate) { return eventRepository.getEventsByEventDate(eventDate); }
//
//    @Transactional
//    public void updateEvent(Event event) {
//        Event existingEvent = eventRepository.findById(event.id);
//        if (existingEvent == null) {
//            // Handle the case where the user is not found
//            throw new RuntimeException("Event not found with id: " + event.id);
//        }
//
//        // Update the fields of the existing user with the new values
//        existingEvent.userId = event.userId != null ? event.userId : existingEvent.userId;
//        existingEvent.eventTarget = event.eventTarget != null ? event.eventTarget : existingEvent.eventTarget;
//        existingEvent.createdAt = event.createdAt != null ? event.createdAt : existingEvent.createdAt;
//        existingEvent.metadata = event.metadata != null ? event.metadata : existingEvent.metadata;
//
//        eventRepository.persist(existingEvent);
//    }
//}
