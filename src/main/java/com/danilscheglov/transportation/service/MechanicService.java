package com.danilscheglov.transportation.service;

import com.danilscheglov.transportation.dto.UserDto;
import com.danilscheglov.transportation.entity.common.UserRole;
import com.danilscheglov.transportation.mapper.UserMapper;
import com.danilscheglov.transportation.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MechanicService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        return userRepository.findAllByRole(UserRole.MECHANIC).stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) {
        return userRepository.findByIdAndRole(id, UserRole.MECHANIC)
                .map(userMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Механик с ID " + id + " не найден"));
    }
}
