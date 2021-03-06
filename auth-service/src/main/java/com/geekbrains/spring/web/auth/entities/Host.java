package com.geekbrains.spring.web.auth.entities;

import com.geekbrains.spring.web.auth.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Table(name = "hosts")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Host {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "role_id", unique = true)
    private Role role;

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

    @Column(name = "title_firm")
    private String titleFirm;

    @Column(name = "country")
    private String country;

    @Column(name = "address")
    private String address;

    @Column(name = "office_address")
    private String officeAddress;

    @Column(name = "postcode")
    private String postcode;

    @Column(name = "inn")
    private Integer inn;

    @Column(name = "account")// или отдельная таблица со счетами
    private Integer account;

//    @ManyToMany
//    @JoinTable(name = "users_roles",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id"))
//    private Collection<Role> roles;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Host(Long id, Role role, User user, String name, String patronymic, String surname, String email, String username, String password, String titleFirm, String country, String officeAddress, String postcode, Integer inn, Integer account) {
        this.id = id;
        this.role = role;
        this.user = user;
        this.name = name;
        this.patronymic = patronymic;
        this.surname = surname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.titleFirm = titleFirm;
        this.country = country;
        this.officeAddress = officeAddress;
        this.postcode = postcode;
        this.inn = inn;
        this.account = account;
    }
}