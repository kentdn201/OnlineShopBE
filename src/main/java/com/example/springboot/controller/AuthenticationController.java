package com.example.springboot.controller;

import com.example.springboot.config.JWTTokenHelper;
import com.example.springboot.config.ObjectMapperUtils;
import com.example.springboot.dto.SignInDto;
import com.example.springboot.dto.TokenDto;
import com.example.springboot.dto.user.UserDto;
import com.example.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody SignInDto signInDto) throws InvalidKeySpecException, NoSuchAlgorithmException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInDto.getUsername(), signInDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();

        String jwtToken = jwtTokenHelper.generateToken(user.getUsername());

        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(jwtToken);

        return ResponseEntity.ok(tokenDto);
    }

    @GetMapping("/auth/userinfo")
    public ResponseEntity<?> getUserInfo(Principal user) {
        User userObj = (User)userDetailsService.loadUserByUsername(user.getName());

        UserDto userDto = ObjectMapperUtils.map(userObj, UserDto.class);
        userDto.setRoles(userObj.getAuthorities().toArray());

        return ResponseEntity.ok(userDto);
    }
}
