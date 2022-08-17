package com.neon.new_booking.core.repositories;

import com.neon.new_booking.core.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o where o.username = ?1")
    List<Order> findAllByUsername(String username);

    @Query("select o from Order o where o.username = ?1 and o.status.description in ('awaiting payment', 'paid', 'booked')")
    List<Order> findActiveByUsername(String username);

    @Query("select o from Order o where o.username = ?1 and o.status.description in ('canceled', 'completed')")
    List<Order> findInactiveByUsername(String username);

    @Query("select o from Order o where o.apartment.username = ?1")
    List<Order> findHostOrdersByUsername(String username);
}
