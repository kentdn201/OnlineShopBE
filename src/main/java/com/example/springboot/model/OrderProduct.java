package com.example.springboot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
public class OrderProduct {
    @EmbeddedId
    private OrderProductKey id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;

    private Double price;

    public OrderProduct(Order order, Product product, Integer quantity, Double price) {
        id = new OrderProductKey();
        id.setOrder(order);
        id.setProduct(product);
        this.quantity = quantity;
        this.price = price;
    }

    @Transient
    public Product getProduct() {
        return this.id.getProduct();
    }

}
