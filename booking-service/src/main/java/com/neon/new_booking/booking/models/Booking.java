package com.neon.new_booking.booking.models;

import com.neon.new_booking.api.bookings.BookingItemDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Booking {
    private List<BookingItem> items;
    private Long itemIdNumerator;

    public Booking() {
        this.items = new ArrayList<>();
        this.itemIdNumerator = 1l;
    }

    public void add(BookingItemDto bookingItemDto) {
        for (BookingItem item : items) {
            if (item.getApartmentId().equals(bookingItemDto.getApartmentId()) &&
                item.getBookingStartDate().equals(bookingItemDto.getBookingStartDate()) &&
                item.getBookingFinishDate().equals(bookingItemDto.getBookingFinishDate()))
                return;
        }
        bookingItemDto.setItemId(itemIdNumerator++);
        items.add(new BookingItem(bookingItemDto));
    }

    public void remove(Long apartmentId, String startData, String finishDate) {
        for (BookingItem item : items) {
            if (item.getApartmentId().equals(apartmentId) &&
                item.getBookingStartDate().equals(startData) &&
                item.getBookingFinishDate().equals(finishDate)
            ) {
                items.remove(item);
                if(items.size() == 0)
                    itemIdNumerator = 1l;
                return;
            }
        }
    }

    public void remove(Long itemId) {
        for (BookingItem item : items) {
            if (item.getItemId().equals(itemId)) {
                items.remove(item);
                if(items.size() == 0)
                    itemIdNumerator = 1l;
                return;
            }
        }
    }

    public BookingItem getItem(Long itemId) {
        for (BookingItem item : items) {
            if (item.getItemId().equals(itemId))
                return item;
        }
        return null;
    }

    public void clear() {
        items.clear();
        itemIdNumerator = 1l;
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
                anotherItem.setItemId(itemIdNumerator++);
                items.add(anotherItem);
            }
        }
        another.clear();
    }


}
