package com.example.springboot.service;

import com.example.springboot.config.ObjectMapperUtils;
import com.example.springboot.dto.order.OrderDetailDto;
import com.example.springboot.dto.order.OrderProductDto;
import com.example.springboot.dto.order.OrderShowDto;
import com.example.springboot.dto.order.OrderStatusDto;
import com.example.springboot.model.OrderProduct;
import com.example.springboot.model.exceptions.CustomException;
import com.example.springboot.model.Enum.Role;
import com.example.springboot.model.Order;
import com.example.springboot.model.User;
import com.example.springboot.repository.OrderProductRepository;
import com.example.springboot.repository.OrderRepository;
import com.example.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderProductService orderProductService;
    @Autowired
    private OrderProductRepository orderProductRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

//  Mapping Dto to Entity
    public List<OrderShowDto> getOrdersByUserId(Integer userId) {
        List<Order> orderListByUser = orderRepository.findByUserId(userId);
        List<OrderShowDto> orderShowDtos = ObjectMapperUtils.mapAll(orderListByUser, OrderShowDto.class);

        return orderShowDtos;
    }

    //  Update getOrdersDetail
    public OrderDetailDto getOrdersDetail(Integer id) {
        Order orderDetail = orderRepository.findOrderById(id);
        if (orderDetail == null) {
            throw new CustomException("Order: " + id + " is not available");
        }
        OrderDetailDto orderDetailDto = ObjectMapperUtils.map(orderDetail, OrderDetailDto.class);
        List<OrderProductDto> orderProductDtos = ObjectMapperUtils.mapAll(orderDetail.getOrderProducts(), OrderProductDto.class);

//      Luc mapping bi null len phai map theo cach cu~
        orderDetailDto.setCreateDate(orderDetail.getCreatedDate());
        orderDetailDto.setUserId(orderDetail.getUser().getId());
        orderDetailDto.setOrderProductDtos(orderProductDtos);
        return orderDetailDto;
    }

    public Order createOrder(Order order) {
        order.setCreatedDate(new Date());
        return orderRepository.save(order);
    }

    public void updateOrder(Order order) {
        orderRepository.save(order);
    }

    public Optional<Order> findOrderById(Integer id) {
        return orderRepository.findById(id);
    }

    public void updateOrderDto(Integer id, OrderStatusDto orderStatusDto) {
        Order existOrder = orderRepository.findOrderById(id);
        existOrder.setOrderStatus(orderStatusDto.getOrderStatus());
        orderRepository.save(existOrder);
    }

    //  Mapping Dto to Entity
    public List<OrderShowDto> getAllOrderDto() {
        List<Order> orderList = orderRepository.findAll();
        List<OrderShowDto> orderShowDtos = ObjectMapperUtils.mapAll(orderList, OrderShowDto.class);

        return orderShowDtos;
    }

    //  Mapping Dto to Entity
    public OrderShowDto getOrderDto(Order order) {
        OrderShowDto orderShowDto = ObjectMapperUtils.map(order, OrderShowDto.class);
        return orderShowDto;
    }
}