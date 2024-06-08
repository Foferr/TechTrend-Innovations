package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Event;
import org.acme.model.Messages;

import java.util.List;

@ApplicationScoped
public class MessagesRepository implements PanacheRepository<Messages> {
    public List<Messages> getByUserId(Long userId) {
        return list("chatHistory.user.id", userId);
    }

    public List<Messages> getByChatId(Long chatHistoryId) {
        return list("chatHistory.id", chatHistoryId);
    }

    public List<Messages> getBySenderType(String senderType) {
        return list("senderType", senderType);
    }

    public List<Messages> getByUserIdAndChatId(Long userId, Long chatHistoryId) {
        return list("chatHistory.user.id = ?1 and chatHistory.id = ?2", userId, chatHistoryId);
    }
}
