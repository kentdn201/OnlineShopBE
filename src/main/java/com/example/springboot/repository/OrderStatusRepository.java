package com.example.springboot.repository;

import com.example.springboot.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Integer> {
    OrderStatus getById(Integer id);
}
