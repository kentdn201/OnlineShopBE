package com.example.springboot.service;

import com.example.springboot.dto.ResponseDto;
import com.example.springboot.dto.SignInDto;
import com.example.springboot.dto.SignupDto;
import com.example.springboot.dto.user.UserUpdateStatusDto;
import com.example.springboot.model.exceptions.CustomException;
import com.example.springboot.model.AuthenticationToken;
import com.example.springboot.model.Enum.Role;
import com.example.springboot.model.Enum.UserStatus;
import com.example.springboot.model.User;
import com.example.springboot.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationSerivce authenticationSerivce;

    @Transactional
    public ResponseDto signUp(SignupDto signupDto) {
//       Check if user present
        if (userRepository.findByEmail(signupDto.getEmail()) != null) {
            throw new CustomException("Tài khoản đã tồn tại");
        }

//      Save the user
        User user = new User();
        user.setEmail(signupDto.getEmail());
        user.setFirstName(signupDto.getFirstName());
        user.setLastName(signupDto.getLastName());
        user.setUsername(signupDto.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(signupDto.getPassword()));
        user.setEnable(true);
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
        User user = userRepository.findByEmail(signInDto.getUsername());
        if (Objects.isNull(user)) {
            throw new CustomException("Tài khoản không có trong hệ thống");
        }
//      hash the password
        if (!user.getPassword().equals(new BCryptPasswordEncoder().encode(signInDto.getPassword()))) {
            throw new CustomException("Sai tài khoản hoặc mật khẩu");
        }

        AuthenticationToken token = authenticationSerivce.getToken(user);
        if (Objects.isNull(token)) {
            throw new CustomException("Token không tồn tại!");
        }

        return new ResponseDto(token.getToken(), "success");

    }

    public User findByUserId(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new CustomException("Người dùng với mã: " + id + " không tồn tại");
        }
        return user.get();
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void updateUserStatus(Integer id, UserUpdateStatusDto userUpdateStatusDto) {
        User existUser = userRepository.findUserById(id);
//        existUser.setUserStatus(userUpdateStatusDto.getUserStatus());
        userRepository.save(existUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", username);
        }
        return user;
    }
}
