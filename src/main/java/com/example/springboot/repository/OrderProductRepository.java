package com.example.springboot.repository;

import com.example.springboot.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer>{
    List<OrderProduct> findByOrderId(Integer id);
}
