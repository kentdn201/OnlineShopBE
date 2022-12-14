package com.example.springboot.repository;

import com.example.springboot.model.Order;
import com.example.springboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findOrderById(Integer id);
    Order getOrderById(Integer id);
    List<Order> findByUserId(Integer userId);
}
