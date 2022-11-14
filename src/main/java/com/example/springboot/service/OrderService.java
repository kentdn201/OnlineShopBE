package com.example.springboot.service;

import com.example.springboot.dto.order.OrderDetailDto;
import com.example.springboot.dto.order.OrderProductDto;
import com.example.springboot.dto.order.OrderShowDto;
import com.example.springboot.exceptions.CustomException;
import com.example.springboot.model.Enum.Role;
import com.example.springboot.model.Order;
import com.example.springboot.model.User;
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

    public List<Order> getAllOrders()
    {
        return orderRepository.findAll();
    }

    public List<OrderShowDto> getOrdersByUserId(Integer userId)
    {
        List<OrderShowDto> orderShowDtos = getAllOrderDto();
        List<OrderShowDto> newOrderShowDtos = new ArrayList<>();

        for (OrderShowDto dto: orderShowDtos)
        {
            if(dto.getUserId() == userId)
            {
                newOrderShowDtos.add(dto);
            }
        }
        return newOrderShowDtos;
    }

    public OrderDetailDto getOrdersDetail(Integer id, Integer userId)
    {
//      Lấy ra đơn hàng cần tìm
        Optional<Order> orderDetail = orderRepository.findById(id);

//      Lấy ra thông tin người dùng
        Optional<User> existUser = userRepository.findById(userId);

        List<OrderProductDto> orderProductsDtos = orderProductService.getAllOrderProductsDtos();
        List<OrderProductDto> newOrderProductsDto = new ArrayList<>();
        OrderDetailDto orderDetailDto = new OrderDetailDto();

//      Check xem order muốn tìm có tồn tại hay không
        if(!orderDetail.isPresent())
        {
            throw new CustomException("Đơn hàng với mã: " + id + " không tồn tại");
        }

//      Check user còn tồn tại hay không và là user thì không được truy cập đến các order khác
        if(existUser.isPresent())
        {
            if(existUser.get().getRole() == Role.User)
            {
                if(orderDetail.get().getUser().getId() != userId)
                {
                    throw new CustomException("Bạn không có quyền truy cập vào " + id);
                }
            }
        } else {
            throw new CustomException("Người Dùng Không Tồn Tại");
        }

        orderDetailDto.setId(orderDetail.get().getId());
        orderDetailDto.setUserId(orderDetail.get().getUser().getId());
        orderDetailDto.setCreateDate(orderDetail.get().getCreatedDate());
        orderDetailDto.setOrderStatus(orderDetail.get().getOrderStatus());
        orderDetailDto.setAddress(orderDetail.get().getAddress());
        orderDetailDto.setTypePayment(orderDetail.get().getTypePayment());
        orderDetailDto.setNote(orderDetail.get().getNote());
        orderDetailDto.setPhoneNumber(orderDetail.get().getPhoneNumber());
        for (OrderProductDto dto: orderProductsDtos)
        {
            if(orderDetailDto.getId() == dto.getOrderId())
            {
                newOrderProductsDto.add(dto);
            }
        }
        orderDetailDto.setOrderProductDtos(newOrderProductsDto);
        return orderDetailDto;
    }

    public Order createOrder(Order order)
    {
        order.setCreatedDate(new Date());
        return orderRepository.save(order);
    }

    public void updateOrder(Order order)
    {
        orderRepository.save(order);
    }

    public List<OrderShowDto> getAllOrderDto()
    {
        List<Order> orderList = orderRepository.findAll();
        List<OrderShowDto> orderShowDtos = new ArrayList<>();

        for (Order order: orderList)
        {
            orderShowDtos.add(getOrderDto(order));
        }

        return orderShowDtos;
    }

    public OrderShowDto getOrderDto(Order order)
    {
        OrderShowDto orderShowDto = new OrderShowDto();
        orderShowDto.setId(order.getId());
        orderShowDto.setCreateDate(order.getCreatedDate());
        orderShowDto.setUserId(order.getUser().getId());
        orderShowDto.setOrderStatus(order.getOrderStatus());
        orderShowDto.setAddress(order.getAddress());
        orderShowDto.setTypePayment(order.getTypePayment());
        orderShowDto.setNote(order.getNote());
        orderShowDto.setPhoneNumber(order.getPhoneNumber());
        return orderShowDto;
    }
}