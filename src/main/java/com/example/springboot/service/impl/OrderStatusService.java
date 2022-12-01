package com.example.springboot.service.impl;

import com.example.springboot.model.OrderStatus;

import java.util.List;

public interface OrderStatusService {
    OrderStatus saveOrderStatus(String name);
    OrderStatus editOrderStatus(Integer id, String name);
    List<OrderStatus> getAll();
    OrderStatus getOneById (Integer id);
}
