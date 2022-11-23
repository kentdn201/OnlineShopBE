package com.example.springboot.model.Enum.repository;

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

    @Query(
            value = "select * from Products p INNER JOIN Category c ON c.id = p.category_id WHERE c.slug = ?1",
            nativeQuery = true)
    List<Product> getProductsByCategorySlug(String slug);
}
