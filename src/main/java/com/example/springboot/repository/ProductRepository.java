package com.example.springboot.repository;

import com.example.springboot.dto.ProductDto;
import com.example.springboot.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findBySlug(String slug);

    Product findBySlugOrId(String slug, Integer id);
}
