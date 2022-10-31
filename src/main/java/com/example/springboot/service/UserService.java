package com.example.springboot.service;

import com.example.springboot.dto.ResponseDto;
import com.example.springboot.dto.SignInDto;
import com.example.springboot.dto.SignupDto;
import com.example.springboot.exceptions.CustomException;
import com.example.springboot.model.AuthenticationToken;
import com.example.springboot.model.User;
import com.example.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationSerivce authenticationSerivce;

    @Transactional
    public ResponseDto signUp(SignupDto signupDto) {
//       Check if user present
        if(userRepository.findByEmail(signupDto.getEmail()) != null){
            throw new CustomException("Tài khoản đã tồn tại");
        }

//      hash password
        String encryptedPassword = signupDto.getPassword();
        try {
            encryptedPassword = hashPassword(signupDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

//      Save the user
        User user = new User();
        user.setEmail(signupDto.getEmail());
        user.setFirstName(signupDto.getFirstName());
        user.setLastName(signupDto.getLastName());
        user.setPassword(encryptedPassword);
        userRepository.save(user);

//      Create the token
        AuthenticationToken token = new AuthenticationToken(user);
        authenticationSerivce.saveToken(token);

        ResponseDto responseDto = new ResponseDto("Create User Successfully", "Success");
        return responseDto;
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        return hash;
    }

    public ResponseDto signIn(SignInDto signInDto) {
//        find user by email
        User user = userRepository.findByEmail(signInDto.getEmail());
        if(Objects.isNull(user))
        {
            throw new CustomException("Tài khoản không có trong hệ thống");
        }
//        hash the password
        try {
            if(!user.getPassword().equals(hashPassword(signInDto.getPassword()))) {
                throw new CustomException("Sai tài khoản hoặc mật khẩu");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        AuthenticationToken token = authenticationSerivce.getToken(user);
        if(Objects.isNull(token))
        {
            throw new CustomException("Token không tồn tại!");
        }

        return new ResponseDto(token.getToken(), "success");

    }
}