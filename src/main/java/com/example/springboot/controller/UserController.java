package com.example.springboot.controller;

import com.example.springboot.common.ApiResponse;
import com.example.springboot.dto.ResponseDto;
import com.example.springboot.dto.SignupDto;
import com.example.springboot.dto.user.UserUpdateStatusDto;
import com.example.springboot.model.User;
import com.example.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getUsers();
    }

    @PostMapping("/signup")
    public ResponseDto signUp(@RequestBody SignupDto signupDto) {
        return userService.signUp(signupDto);
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable(name = "userId") Integer userId) {
        return userService.findByUserId(userId);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<ApiResponse> updateUserStatus(@PathVariable(name = "userId") Integer userId, @RequestBody UserUpdateStatusDto userUpdateStatusDto) {
        userService.updateUserStatus(userId, userUpdateStatusDto);
        return new ResponseEntity<>(new ApiResponse(true, "Update Success"), HttpStatus.OK);
    }
}

