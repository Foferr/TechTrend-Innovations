package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Messages;

@ApplicationScoped
public class MessagesRepository implements PanacheRepository<Messages> {
}
