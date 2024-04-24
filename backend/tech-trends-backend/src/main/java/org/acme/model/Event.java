package org.acme.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "EventLog")
public class Event extends PanacheEntity {


    @Column(name = "user_id", nullable = false)
    public Long userId;

    @Column(name = "event_target", nullable = false)
    public String eventTarget;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @Column(name = "metadata", nullable = false)
    public String metadata;

}
