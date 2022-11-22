package com.example.springboot.repository;

import com.example.springboot.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findBySlug(String slug);

    Category findBySlugAndId(String slug, Integer id);
    Category findBySlugOrId(String slug, Integer id);
}
