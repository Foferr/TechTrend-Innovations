package org.acme.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "Messages")
public class Messages extends PanacheEntity {
    @ManyToOne
    @JoinColumn(name = "chat_history_id", nullable = false)
    private ChatHistory chatHistory;

    @Column(name = "sender_type", length = 25, nullable = false)
    private String senderType;

    @Column(name = "message_content", columnDefinition = "TEXT", nullable = false)
    private String messageContent;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Constructors
    public Messages() {
        // Default constructor
    }

    public Messages(ChatHistory chatHistory, String senderType, String messageContent) {
        this.chatHistory = chatHistory;
        this.senderType = senderType;
        this.messageContent = messageContent;
    }

    // Getters and setters
    public ChatHistory getChatHistory() {
        return chatHistory;
    }

    public void setChatHistory(ChatHistory chatHistory) {
        this.chatHistory = chatHistory;
    }

    public String getSenderType() {
        return senderType;
    }

    public void setSenderType(String senderType) {
        this.senderType = senderType;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
