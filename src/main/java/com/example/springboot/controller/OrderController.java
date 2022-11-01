package com.example.springboot.controller;

import com.example.springboot.dto.checkout.CheckoutItemDto;
import com.example.springboot.dto.checkout.StripeResponse;
import com.example.springboot.service.AuthenticationSerivce;
import com.example.springboot.service.OrderService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private AuthenticationSerivce authenticationSerivce;
    @Autowired
    private OrderService orderService;

    @PostMapping("/create-checkout-session")
    public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<CheckoutItemDto> checkoutItemDtoList)
            throws StripeException
    {
        Session session = orderService.createSesion(checkoutItemDtoList);
        StripeResponse stripeResponse = new StripeResponse(session.getId());
        return new ResponseEntity<>(stripeResponse, HttpStatus.OK);
    }

}
