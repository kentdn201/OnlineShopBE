package com.example.springboot.repository;

import com.example.springboot.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findBySlug(String slug);

    @Query("SELECT p FROM Product p WHERE " +
            "p.name LIKE CONCAT('%', :keyword, '%')" +
            "Or p.description LIKE CONCAT('%', :keyword, '%')")
    List<Product> searchProducts(String keyword);

//    @Query(value = "SELECT p FROM products p WHERE " +
//            "p.name LIKE CONCAT('%', :keyword, '%')" +
//            "Or p.description LIKE CONCAT('%', :keyword, '%')", nativeQuery = true)
//    List<Product> searchProductsSQL(String keyword);
}
