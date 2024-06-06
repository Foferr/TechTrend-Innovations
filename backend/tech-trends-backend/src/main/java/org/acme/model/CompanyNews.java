package org.acme.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "CompanyNews")
public class CompanyNews extends PanacheEntity {

    @Column(name = "title", nullable = false)
    public String title;

    @Column(name = "news_content", nullable = false)
    public String newsContent;

    @ManyToOne(optional = false)
    @JoinColumn(name = "created_by_admin_id", referencedColumnName = "id", nullable = false)
    public User user;

    @Column(name = "status", nullable = false)
    public String status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt = LocalDateTime.now();

    public void setUser(User user) {
        this.user = user;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setContent(String newsContent) {
        this.newsContent=newsContent;
    }
}
