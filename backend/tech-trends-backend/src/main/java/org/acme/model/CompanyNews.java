package org.acme.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "companynews")
public class CompanyNews extends PanacheEntity {



    @Column(name = "id", nullable = false , insertable=false, updatable=false)
    public Long companynews_id;


    @Column(name = "title", nullable = false)
    public String title;

    @Column(name = "news_content", nullable = false)
    public String news_content;

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
}
