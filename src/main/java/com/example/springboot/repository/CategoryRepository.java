package com.example.springboot.repository;

import com.example.springboot.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findBySlug(String slug);
}
