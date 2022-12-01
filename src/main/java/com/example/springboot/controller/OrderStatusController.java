package com.example.springboot.controller;

import com.example.springboot.dto.OrderStatus.OrderStatusDto;
import com.example.springboot.model.OrderStatus;
import com.example.springboot.service.OrderStatusServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/status")
public class OrderStatusController {
    @Autowired
    private OrderStatusServiceImpl orderStatusService;

    @GetMapping("/all")
    private List<OrderStatus> getAllOrderStatus() {
        return orderStatusService.getAll();
    }

    @PostMapping("/create")
    private OrderStatus createUserRole(@RequestBody OrderStatusDto orderStatusDto) {
        return orderStatusService.saveOrderStatus(orderStatusDto.getName());
    }

    @PutMapping("/edit/{id}")
    private OrderStatus editOrderStatus(@PathVariable(name = "id") Integer id, @RequestBody OrderStatusDto orderStatusDto) {
        return orderStatusService.editOrderStatus(id, orderStatusDto.getName());
    }
}
