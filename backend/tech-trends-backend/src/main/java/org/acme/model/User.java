package org.acme.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Users")
public class User extends PanacheEntity {


    @Column(name = "first_name", nullable = false)
    public String firstName;

    @Column(name = "last_name", nullable = false)
    public String lastName;

    @Column(name = "lang", nullable = false)
    public String language;

    @Column(name = "birthday", nullable = false)
    public LocalDate birthday;

    @Column(name = "email", nullable = false, unique = true)
    public String email;

    @Column(name = "user_password", nullable = false)
    public String userPassword;

    @Column(name = "phone")
    public String phone;

    @Column(name = "user_type", nullable = false)
    public String userType;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;
}
