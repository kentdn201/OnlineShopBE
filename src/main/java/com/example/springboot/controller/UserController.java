package com.example.springboot.controller;

import com.example.springboot.dto.ResponseDto;
import com.example.springboot.dto.SignInDto;
import com.example.springboot.dto.SignupDto;
import com.example.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseDto signUp(@RequestBody SignupDto signupDto) {
        return userService.signUp(signupDto);
    }

    @PostMapping("/signin")
    public ResponseDto signIn(@RequestBody SignInDto signInDto)
    {
        return userService.signIn(signInDto);
    }

}
