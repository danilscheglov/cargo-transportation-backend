package com.danilscheglov.transportation.controller;

import com.danilscheglov.transportation.dto.UserDto;
import com.danilscheglov.transportation.entity.User;
import com.danilscheglov.transportation.mapper.UserMapper;
import com.danilscheglov.transportation.repository.UserRepository;
import com.danilscheglov.transportation.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto user) {
        System.out.println("user = " + user);
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Username taken");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(userMapper.fromDto(user));
        return ResponseEntity.ok("Registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        return userRepository.findByEmail(user.getEmail())
                .filter(u -> passwordEncoder.matches(user.getPassword(), u.getPassword()))
                .map(u -> jwtUtil.generateToken(u.getEmail(), u.getRole()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials"));
    }
}
