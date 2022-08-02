package com.geekbrains.spring.web.core.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "apartments")
@Getter
@Setter
@NoArgsConstructor
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn (name = "apartment_category_id")
    private ApartmentCategory apartmentCategory;

    @Embedded
    private Address address;

    @Column (name = "square_meters")
    private Integer squareMeters;

    @Column (name = "number_of_guests")
    private Integer numberOfGuests;

    @Column (name = "number_of_rooms")
    private Integer numberOfRooms;

    @Column (name = "number_of_beds")
    private Integer numberOfBeds;

    @Column(name = "price_per_night")
    private BigDecimal pricePerNight;

    @OneToMany(mappedBy = "apartment",cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<BookingDate> bookingDates;

    @Column(name = "username")
    private String username;

    //TODO добавить поля check-in, check-out
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ApartmentStatus status;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @Override
    public String toString() {
        return "Apartment{" +
                "id='" + id + '\'' +
                "title='" + title + '\'' +
                ", apartmentCategory=" + apartmentCategory.getTitle() +
                ", city='" + address.getCity() + '\'' +
                ", street='" + address.getStreet() + '\'' +
                ", buildingNumber=" + address.getBuildingNumber() +
                ", squareMeters=" + squareMeters +
                ", numberOfGuests=" + numberOfGuests +
                ", numberOfRooms=" + numberOfRooms +
                ", numberOfBeds=" + numberOfBeds +
                ", pricePerNight=" + pricePerNight +
                ", username= '" + username + '\'' +
                '}';
    }

    public static class Builder {
        private final Apartment apartment;

        public Builder() {
            this.apartment = new Apartment();
        }

        public Builder id(Long id) {
            apartment.id = id;
            return this;
        }

        public Builder title(String title) {
            apartment.title = title;
            return this;
        }

        public Builder pricePerNight(BigDecimal pricePerNight) {
            apartment.pricePerNight = pricePerNight;
            return this;
        }

        //продумать выбор имеющейся категории
        public Builder apartmentCategory(ApartmentCategory apartmentCategory) {
            apartment.apartmentCategory = apartmentCategory;
            return this;
        }


        public Builder address(Address address) {
            apartment.address = address;
            return this;
        }


        public Builder squareMeters(Integer squareMeters) {
            apartment.squareMeters = squareMeters;
            return this;
        }

        public Builder numberOfGuests(Integer numberOfGuests) {
            apartment.numberOfGuests = numberOfGuests;
            return this;
        }

        public Builder numberOfRooms(Integer numberOfRooms) {
            apartment.numberOfRooms = numberOfRooms;
            return this;
        }

        public Builder numberOfBeds(Integer numberOfBeds) {
            apartment.numberOfBeds = numberOfBeds;
            return this;
        }

        public Builder username(String username) {
            apartment.username = username;
            return this;
        }

        public Apartment build() {
            return apartment;
        }
    }

}
