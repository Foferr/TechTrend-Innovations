package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.ChatHistory;


import java.util.List;

@ApplicationScoped
public class ChatHistoryRepository implements PanacheRepository<ChatHistory> {
    //Panache repository implements the basic functionality
    // Custom database queries go here
    public List<ChatHistory> findbyUserId(Long userId) {
        return list("user.id", userId);
    }

    public List<ChatHistory> findChatHistoriesByUserIdAndStatus(Long userId, String status) {
        return list("user.id = ?1 and status = ?2", userId, status);
    }
}