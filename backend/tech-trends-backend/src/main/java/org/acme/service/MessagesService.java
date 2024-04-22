package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.model.Messages;
import org.acme.repository.MessagesRepository;

import java.util.List;

@ApplicationScoped
public class MessagesService {

    @Inject
    MessagesRepository messagesRepository;

    public List<Messages> getAllMessages() {
        return messagesRepository.listAll();
    }

    @Transactional
    public void postMessage(Messages message) {
        messagesRepository.persist(message);
    }

    public List<Messages> getMessagesByUserId(String userId) {
        return messagesRepository.find("userId", userId).list();
    }
}
