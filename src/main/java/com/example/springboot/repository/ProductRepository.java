package com.example.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.springboot.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findBySlug(String slug);
}