package com.example.springboot.controller;

import com.example.springboot.common.ApiResponse;
import com.example.springboot.dto.order.OrderDetailDto;
import com.example.springboot.dto.order.OrderDto;
import com.example.springboot.dto.order.OrderShowDto;
import com.example.springboot.dto.order.ProductOrderDto;
import com.example.springboot.model.Enum.OrderStatus;
import com.example.springboot.model.Order;
import com.example.springboot.model.OrderProduct;
import com.example.springboot.model.User;
import com.example.springboot.repository.OrderRepository;
import com.example.springboot.repository.UserRepository;
import com.example.springboot.service.OrderProductService;
import com.example.springboot.service.OrderService;
import com.example.springboot.service.ProductService;
import com.example.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderProductService orderProductService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/all")
    public List<OrderShowDto> getAllOrders()
    {
        List<OrderShowDto> orderList = orderService.getAllOrderDto();
        return orderList;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<OrderShowDto>> getOrderByUserId(@PathVariable(name = "userId") Integer userId)
    {
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
    }

    @GetMapping("/don-hang/{id}/{userId}")
    public ResponseEntity<OrderDetailDto> getOrderDetail(@PathVariable(name = "id") Integer id, @PathVariable(name = "userId") Integer userId)
    {
        return ResponseEntity.ok(orderService.getOrdersDetail(id, userId));
    }


    @PostMapping("/create/{userId}")
    public ResponseEntity<ApiResponse> create(@PathVariable(name = "userId") Integer userId, @RequestBody OrderDto orderDto)
    {
        List<ProductOrderDto> productOrderDtos = orderDto.getProductOrderDtos();
        Optional<User> findUserById = userRepository.findById(userId);

        if(!findUserById.isPresent())
        {
            return new ResponseEntity<>(new ApiResponse(false, "Tài Khoản Đã Bị Xóa Hoặc Không Tồn Tại"), HttpStatus.BAD_REQUEST);
        }
        User existUser = userRepository.findByEmail(findUserById.get().getEmail());
        Order order = new Order();
        order.setUser(existUser);
        order.setOrderStatus(OrderStatus.NotDelivery);
        order.setAddress(orderDto.getAddress());
        order.setTypePayment(orderDto.getTypePayment());
        order.setNote(orderDto.getNote());
        order.setPhoneNumber(orderDto.getPhoneNumber());

        orderService.createOrder(order);

        List<OrderProduct> orderProducts = new ArrayList<>();
        for(ProductOrderDto productOrderDto: productOrderDtos)
        {
            orderProducts.add(orderProductService.createOrderProduct(new OrderProduct(order, productService.findById(productOrderDto.getId()), productOrderDto.getQuantity(), productOrderDto.getPrice())));
        }

        order.setOrderProducts(orderProducts);
        orderService.updateOrder(order);

        return new ResponseEntity<>(new ApiResponse(true, "Order Thành Công"), HttpStatus.CREATED);
    }
}
