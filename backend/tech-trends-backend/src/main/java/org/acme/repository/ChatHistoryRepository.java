package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.ChatHistory;

@ApplicationScoped
public class ChatHistoryRepository implements PanacheRepository<ChatHistory> {
    //Panache repository implements the basic functionality
    // Custom database queries go here
}
