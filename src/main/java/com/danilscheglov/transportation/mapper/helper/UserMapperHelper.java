package com.danilscheglov.transportation.mapper.helper;

import com.danilscheglov.transportation.entity.User;
import com.danilscheglov.transportation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapperHelper {

    private final UserRepository userRepository;

    public User resolveUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь с email " + email + " не найден"));
    }
}
