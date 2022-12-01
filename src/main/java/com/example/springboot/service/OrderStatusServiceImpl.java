package com.example.springboot.service;

import com.example.springboot.model.OrderStatus;
import com.example.springboot.repository.OrderStatusRepository;
import com.example.springboot.service.impl.OrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderStatusServiceImpl implements OrderStatusService {
    @Autowired
    private OrderStatusRepository orderStatusRepository;
    @Override
    public OrderStatus saveOrderStatus(String name) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setName(name);
        return orderStatusRepository.save(orderStatus);
    }

    @Override
    public OrderStatus editOrderStatus(Integer id, String name) {
        OrderStatus existOrderStatus = orderStatusRepository.getById(id);
        existOrderStatus.setName(name);
        return orderStatusRepository.save(existOrderStatus);
    }

    @Override
    public List<OrderStatus> getAll() {
        return orderStatusRepository.findAll();
    }

    @Override
    public OrderStatus getOneById(Integer id) {
        return orderStatusRepository.getById(id);
    }
}
