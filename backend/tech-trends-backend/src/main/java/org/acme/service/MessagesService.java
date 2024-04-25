package org.acme.service;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.model.Messages;
import org.acme.repository.MessagesRepository;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class MessagesService {


    @Inject
    MessagesRepository messagesRepository;

    public List<Messages> getAllMessages() {
        return messagesRepository.listAll();
    }

    @Transactional
    public void postMessage(Messages messages) {
        messagesRepository.persist(messages);
    }

    public List<Messages> getMessagesByUserId(String userId) {
        return messagesRepository.find("chatHistory.user.id", userId).list();
    }

    public List<Messages> getMessagesByUserIdChatId(String userId, String chatId) {
        return messagesRepository.find("chatHistory.user.id = ?1 and chatHistory.id = ?2", userId, chatId).list();
    }

    public List<Messages> getMessagesByChatId(String chatId) {
        return messagesRepository.find("chatHistory.id", chatId).list();
    }

    public List<Messages> getMessagesBySenderType(String SenderType) {
        return messagesRepository.find("SenderType", SenderType).list();
    }
}
