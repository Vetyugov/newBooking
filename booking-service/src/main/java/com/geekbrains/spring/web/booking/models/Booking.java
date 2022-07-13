package com.geekbrains.spring.web.booking.models;

import com.geekbrains.spring.web.api.core.ApartmentDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
public class Booking {
    private List<BookingItem> items;
    private BigDecimal totalPrice;

    public Booking() {
        this.items = new ArrayList<>();
    }

    public void add(ApartmentDto apartmentDto) {
        if (add(apartmentDto.getId())) {
            return;
        }
        items.add(new BookingItem(apartmentDto));
        recalculate();
    }

    public boolean add(Long id) {
        for (BookingItem o : items) {
            if (o.getApartmentId().equals(id)) {
                //TODO ЖЕНЯ
//                o.changeQuantity(1);
                recalculate();
                return true;
            }
        }
        return false;
    }

    public void decrement(Long apartmentId) {
        Iterator<BookingItem> iter = items.iterator();
        while (iter.hasNext()) {
            BookingItem o = iter.next();
            if (o.getApartmentId().equals(apartmentId)) {
                //TODO ЖЕНЯ
//                o.changeQuantity(-1);
//                if (o.getQuantity() <= 0) {
//                    iter.remove();
//                }
//                recalculate();
                return;
            }
        }
    }

    public void remove(Long apartmentId) {
        items.removeIf(o -> o.getApartmentId().equals(apartmentId));
        recalculate();
    }

    public void clear() {
        items.clear();
        totalPrice = BigDecimal.ZERO;
    }

    private void recalculate() {
        totalPrice = BigDecimal.ZERO;
        for (BookingItem o : items) {
            //TODO ЖЕНЯ
//            totalPrice = totalPrice.add(o.getPrice());
        }
    }

    public void merge(Booking another) {
        for (BookingItem anotherItem : another.items) {
            boolean merged = false;
            for (BookingItem myItem : items) {
                if (myItem.getApartmentId().equals(anotherItem.getApartmentId())) {
                    //TODO ЖЕНЯ
//                    myItem.changeQuantity(anotherItem.getQuantity());
                    merged = true;
                    break;
                }
            }
            if (!merged) {
                items.add(anotherItem);
            }
        }
        recalculate();
        another.clear();
    }
}
