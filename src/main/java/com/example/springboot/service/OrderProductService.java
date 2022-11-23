package com.example.springboot.service;

import com.example.springboot.dto.order.OrderProductDto;
import com.example.springboot.model.OrderProduct;
import com.example.springboot.repository.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderProductService {
    @Autowired
    private OrderProductRepository orderProductRepository;

    public OrderProduct createOrderProduct(OrderProduct orderProduct) {
        return orderProductRepository.save(orderProduct);
    }

    public List<OrderProductDto> getAllOrderProductsDtos() {
        List<OrderProduct> orderProducts = orderProductRepository.findAll();
        List<OrderProductDto> orderProductDtos = new ArrayList<>();

        for (OrderProduct orderProduct : orderProducts) {
            orderProductDtos.add(getProductOrderDto(orderProduct));
        }
        return orderProductDtos;
    }

    public OrderProductDto getProductOrderDto(OrderProduct orderProduct) {
        OrderProductDto productOrderDto = new OrderProductDto();
        productOrderDto.setOrderId(orderProduct.getOrder().getId());
        productOrderDto.setProductId(orderProduct.getProduct().getId());
        productOrderDto.setQuantity(orderProduct.getQuantity());
        productOrderDto.setPrice(orderProduct.getPrice());
        return productOrderDto;
    }
}
