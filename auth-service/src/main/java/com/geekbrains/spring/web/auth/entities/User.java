package com.geekbrains.spring.web.auth.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

//    @Column(name = "title_firm")
//    private String titleFirm;
//
//    @Column(name = "country")
//    private String country;
//
//    @Column(name = "address")
//    private String address;
//
//    @Column(name = "office_address")
//    private String officeAddress;
//
//    @Column(name = "postcode")
//    private Integer postcode;
//
//    @Column(name = "inn")
//    private Integer inn;
//
//    @Column(name = "account")// или отдельная таблица со счетами
//    private Integer account;

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}