package com.example.springboot.repository;

import com.example.springboot.dto.ProductDto;
import com.example.springboot.model.Category;
import com.example.springboot.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findBySlug(String slug);

    Category findBySlugAndId(String slug, Integer id);
    Category findBySlugOrId(String slug, Integer id);

    @Query(
            value = "DELETE FROM category c WHERE c.id IN (SELECT category_id FROM products p where p.category_id = ?1) OR c.id = ?1",
            nativeQuery = true)
    void deleteCategoryById(Integer id);

}
