package com.example.springboot.service;

import com.example.springboot.model.AuthenticationToken;
import com.example.springboot.model.User;
import com.example.springboot.model.Enum.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Objects;

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

    public User getUser(String token) {
        AuthenticationToken authenticationToken = tokenRepository.findByToken(token);
//       Nếu Token này không tồn tại thì chả về null
        if (Objects.isNull(authenticationToken)) {
            return null;
        }
        return authenticationToken.getUser();
    }

    public void authenticate(String token) throws AuthenticationException {
        if (Objects.isNull(token)) {
            throw new AuthenticationException("Token isn't present");
        }
        if (Objects.isNull(getUser(token))) {
            throw new AuthenticationException("Token not valid");
        }
    }
}
