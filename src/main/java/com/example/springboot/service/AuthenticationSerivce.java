package com.example.springboot.service;

import com.example.springboot.model.AuthenticationToken;
import com.example.springboot.model.User;
import com.example.springboot.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationSerivce {
    @Autowired
    private TokenRepository tokenRepository;
    public void saveToken(AuthenticationToken token) {
        tokenRepository.save(token);
    }

    public AuthenticationToken getToken(User user) {
        return tokenRepository.findByUser(user);
    }
}
