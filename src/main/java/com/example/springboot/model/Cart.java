package com.example.springboot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart")
public class Cart {
//    1 người dùng thì có thể xem cart của người đó thôi -> getAllById()
//    admin thì có thể xem cart của tất cả mọi người

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int quantity;

    private double totalPrice;
}
