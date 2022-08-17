package com.neon.new_booking.core.repositories;

import com.neon.new_booking.core.entities.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
    @Query("select s from OrderStatus s where s.description = ?1")
    public Optional<OrderStatus> findByDesc(String statusDesc);

}
