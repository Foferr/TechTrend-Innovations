package org.acme.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "FAQ")
public class Faq extends PanacheEntity {

    @Column(name = "question", nullable = false, length = 1000)
    public String question;

    @Column(name = "answer", nullable = false, length = 4000)
    public String answer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "created_by_admin_id", referencedColumnName = "id", nullable = false)
    public User admin;

    @Column(name = "status", nullable = false)
    public String status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public User getAdmin() {
        return admin;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
