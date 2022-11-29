package com.example.springboot.service;

import com.example.springboot.dto.ResponseDto;
import com.example.springboot.dto.SignupDto;
import com.example.springboot.dto.user.UserUpdateStatusDto;
import com.example.springboot.model.exceptions.CustomException;
import com.example.springboot.model.User;
import com.example.springboot.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public ResponseDto signUp(SignupDto signupDto) {
//       Check if user present
        if (userRepository.findByEmail(signupDto.getEmail()) != null) {
            throw new CustomException("This account is available please try again");
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

        return new ResponseDto("Create User Successfully", "Success");
    }

    public User findByUserId(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new CustomException("User id: " + id + " is not available");
        }
        return user.get();
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void updateUserStatus(Integer id, UserUpdateStatusDto userUpdateStatusDto) {
        User existUser = userRepository.findUserById(id);
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
