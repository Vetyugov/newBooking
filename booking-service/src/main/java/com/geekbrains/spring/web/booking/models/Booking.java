package com.geekbrains.spring.web.booking.models;

import com.geekbrains.spring.web.api.bookings.BookingItemDto;
import com.geekbrains.spring.web.api.core.ApartmentDto;
import com.geekbrains.spring.web.api.core.BookingApartmentDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
public class Booking {
    private List<BookingItem> items;

    public Booking() {
        this.items = new ArrayList<>();
    }

    public void add(BookingItemDto bookingItemDto) {
        for (BookingItem item : items) {
            if (item.getApartmentId().equals(bookingItemDto.getApartmentId()) &&
                item.getBookingStartDate().equals(bookingItemDto.getBookingStartDate()) &&
                item.getBookingFinishDate().equals(bookingItemDto.getBookingFinishDate()))
                return;
        }
        items.add(new BookingItem(bookingItemDto));
    }

    public void remove(Long apartmentId, String startData, String finishDate) {
        for (BookingItem item : items) {
            if (item.getApartmentId().equals(apartmentId) &&
                item.getBookingStartDate().equals(startData) &&
                item.getBookingFinishDate().equals(finishDate)
            ) {
                items.remove(item);
                return;
            }
        }
    }

    public void clear() {
        items.clear();
    }


    public void merge(Booking another) {
        for (BookingItem anotherItem : another.items) {
            boolean merged = false;
            for (BookingItem myItem : items) {
                if (myItem.getApartmentId().equals(anotherItem.getApartmentId()) &&
                    myItem.getBookingStartDate().equals(anotherItem.getBookingStartDate()) &&
                    myItem.getBookingFinishDate().equals(anotherItem.getBookingFinishDate())
                ) {
                    merged = true;
                    break;
                }
            }
            if (!merged) {
                items.add(anotherItem);
            }
        }
        another.clear();
    }
}
