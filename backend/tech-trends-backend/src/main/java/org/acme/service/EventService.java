package org.acme.service;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.DTO.decryptionDTOs.DecryptedEventDTO;
import org.acme.model.Event;
import org.acme.model.User;
import org.acme.repository.EventRepository;
import org.acme.utils.DTOUtils;
import org.acme.utils.EncryptionUtil;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class EventService {

    private static final Logger LOGGER = Logger.getLogger(EventService.class.getName());


    @Inject
    EventRepository eventRepository;

    @Transactional
    public void createEvent(Event event) {
        try {
            event.setEventTarget(EncryptionUtil.encrypt(event.getEventTarget()));
            event.setMetadata(EncryptionUtil.encrypt(event.getMetadata()));
            eventRepository.persist(event);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting event data", e);
        }

    }

    @Transactional
    public void deleteAllEvents() { eventRepository.deleteAll(); }

    @Transactional
    public void deleteEventById(Long id) { eventRepository.deleteById(id); }

    public Optional<DecryptedEventDTO> getEventById(Long eventId) {
        Event event =  eventRepository.findById(eventId);
        if(event == null) {
            return Optional.empty();
        }
        try {
            DecryptedEventDTO dto = DTOUtils.mapToDTO(event);
            return Optional.of(dto);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error while mapping event to DTO", e);
            return Optional.empty();
        }
    }

    public List<DecryptedEventDTO> getAllEvents() {
        List<Event> events = eventRepository.listAll();
        List<DecryptedEventDTO> DTOs = new ArrayList<>();

        for(Event event : events) {
            DTOs.add(DTOUtils.mapToDTO(event));
        }
        return DTOs;
    }

    public List<DecryptedEventDTO> getEventsByUserId(long userId) {
        List<Event> events =  eventRepository.getEventsByUserId(userId);
        List<DecryptedEventDTO> DTOs = new ArrayList<>();

        for(Event event : events) {
            DTOs.add(DTOUtils.mapToDTO(event));
        }
        return DTOs;
    }

    public List<DecryptedEventDTO> getEventsByEventTarget(String eventTarget) {
        try {
            List<Event> events =  eventRepository.getEventsByEventTarget(EncryptionUtil.encrypt(eventTarget));
            List<DecryptedEventDTO> DTOs = new ArrayList<>();

            for(Event event : events) {
                DTOs.add(DTOUtils.mapToDTO(event));
            }
            return DTOs;
        } catch (Exception e) {
            throw new RuntimeException("Couldn't encrypt eventTarget for search");
        }
    }

    public List<DecryptedEventDTO> getEventsByEventDate(LocalDate eventDate) {
        List<Event> events =  eventRepository.getEventsByEventDate(eventDate);
        List<DecryptedEventDTO> DTOs = new ArrayList<>();

        for(Event event : events) {
            DTOs.add(DTOUtils.mapToDTO(event));
        }
        return DTOs;
    }

    @Transactional
    public void updateEvent(Long eventLogId, Event event) {
        Event existingEvent = eventRepository.findById(eventLogId);
        if (existingEvent == null) {
            // Handle the case where the user is not found
            throw new RuntimeException("Event not found with id: " + event.id);
        }

        try {
            existingEvent.setUserId(event.getUserId() != null ? event.getUserId() : existingEvent.getUserId());
            existingEvent.setEventTarget(event.getEventTarget() != null ? EncryptionUtil.encrypt(event.getEventTarget()) : existingEvent.getEventTarget());
            existingEvent.setMetadata(event.getMetadata() != null ? EncryptionUtil.encrypt(event.getMetadata()) : existingEvent.getMetadata());
            eventRepository.persist(existingEvent);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting event data");
        }
    }
}
