package com.example.springboot.repository;

import com.example.springboot.model.Cart;
import com.example.springboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findAllByUser(User user);
}
