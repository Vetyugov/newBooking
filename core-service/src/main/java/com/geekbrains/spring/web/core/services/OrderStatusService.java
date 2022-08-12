package com.geekbrains.spring.web.core.services;

import com.geekbrains.spring.web.core.entities.Order;
import com.geekbrains.spring.web.core.entities.OrderStatus;
import com.geekbrains.spring.web.core.repositories.OrderStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderStatusService {
    private final OrderStatusRepository orderStatusRepository;

    public Optional<OrderStatus> findById(Long id) {
        return orderStatusRepository.findById(id);
    }

    public Optional<OrderStatus> findByDesc(String desc) {
        return orderStatusRepository.findByDesc(desc);
    }

    public List<OrderStatus> findAll(){
        return orderStatusRepository.findAll();
    }
}
