package com.geekbrains.spring.web.auth.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @Column(name = "name")
    private String name;

    @Column(name = "patronymic")//отчество
    private String patronymic;

    @Column(name = "surname")
    private String surname;

    @Column(name = "username", unique = true)
    private String username;

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

    @Column(name = "account")
    private String account;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Host(Long id, User user, String name, String patronymic, String surname, String username, String titleFirm, String country, String officeAddress, String postcode, Integer inn, String account) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.patronymic = patronymic;
        this.surname = surname;
        this.username = username;
        this.titleFirm = titleFirm;
        this.country = country;
        this.officeAddress = officeAddress;
        this.postcode = postcode;
        this.inn = inn;
        this.account = account;
    }

    public Host(Long id, User user, String name, String patronymic, String surname, String username, String country, String address, String postcode, Integer inn, String account) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.patronymic = patronymic;
        this.surname = surname;
        this.username = username;
        this.country = country;
        this.address = address;
        this.postcode = postcode;
        this.inn = inn;
        this.account = account;
    }
}