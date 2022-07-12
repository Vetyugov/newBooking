package com.geekbrains.spring.web.auth.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "guests")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @Column(name = "name")
    private String name;

    @Column(name = "patronymic")//отчество
    private String patronymic;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Guest(Long id, User user, String name, String patronymic, String surname, String email, String username, String password) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.patronymic = patronymic;
        this.surname = surname;
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
