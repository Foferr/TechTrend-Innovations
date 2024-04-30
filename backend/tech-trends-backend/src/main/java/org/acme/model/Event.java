//package org.acme.model;
//
//import io.quarkus.hibernate.orm.panache.PanacheEntity;
//import jakarta.persistence.*;
//import org.hibernate.annotations.CreationTimestamp;
//
//import java.time.LocalDate;
//
//@Entity
//@Table(name = "EventLog")
//public class Event extends PanacheEntity {
//
//
//    @Column(name = "user_id", nullable = false)
//    public Long userId;
//
//    @Column(name = "event_target", nullable = false)
//    public String eventTarget;
//
//    @CreationTimestamp
//    @Column(name = "created_at", nullable = false, updatable = false)
//    public LocalDate createdAt;
//
//    @Column(name = "metadata", nullable = false)
//    public String metadata;
//
//    // Getter and setter methods
//
//    public Long  getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Long userId) {
//        this.userId = userId;
//    }
//
//    public String getEventTarget() {
//        return eventTarget;
//    }
//
//    public void setEventTarget(String eventTarget) {
//        this.eventTarget = eventTarget;
//    }
//
//    public LocalDate getCreatedAt() { return createdAt; }
//
//    public void setCreatedAt(LocalDate createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public String getMetadata() {
//        return metadata;
//    }
//
//    public void setMetadata(String metadata) { this.metadata = metadata; }
//}
