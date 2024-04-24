package org.acme.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Messages")
public class Messages extends PanacheEntity {

    @ManyToOne
    @JoinColumn(name = "chat_history_id", nullable = false)
    public ChatHistory chatHistory;

    @Column(name = "sender_type", nullable = false)
    public String SenderType;

    @Column(name = "message_content", nullable = false)
    public String MessageContent;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime CreatedAt;
}
