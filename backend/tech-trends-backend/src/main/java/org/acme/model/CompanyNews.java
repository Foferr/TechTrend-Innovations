package org.acme.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "CompanyNews")
public class CompanyNews extends PanacheEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "companynews_id", referencedColumnName = "id", nullable = false)
    public Long newsId;

    @Column(name = "title", nullable = false)
    public String title;

    @Column(name = "news_content", nullable = false)
    public String news_content;

    @Column(name = "created_by_admin_id", nullable = false)
    public User user;

    @Column(name = "status", nullable = false)
    public String status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt = LocalDateTime.now();


}