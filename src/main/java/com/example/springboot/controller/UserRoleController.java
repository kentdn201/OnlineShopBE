package com.example.springboot.controller;

import com.example.springboot.model.UserRole;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/role")
public class UserRoleController {

    @PostMapping("/create")
    private UserRole createUserRole(@RequestBody UserRole userRole) {
        UserRole newRole = new UserRole();
        newRole.setRoleName(userRole.getRoleName());
        newRole.setRoleCode(userRole.getRoleCode());
        return newRole;
    }
}
