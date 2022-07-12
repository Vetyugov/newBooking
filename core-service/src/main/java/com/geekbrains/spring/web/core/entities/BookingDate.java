package com.geekbrains.spring.web.core.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking_dates")
@Data
public class BookingDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column (name = "booking_start_date")
    private LocalDate startDate;

    @Column (name = "booking_finish_date")
    private LocalDate finishDate;

    @ManyToOne
    @JoinColumn(name = "apartment_id")
    private Apartment apartment;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
