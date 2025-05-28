package com.danilscheglov.transportation.service;

import com.danilscheglov.transportation.dto.UserDto;
import com.danilscheglov.transportation.entity.User;
import com.danilscheglov.transportation.entity.common.UserRole;
import com.danilscheglov.transportation.exception.ResourceNotFoundException;
import com.danilscheglov.transportation.mapper.UserMapper;
import com.danilscheglov.transportation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> getAllDrivers() {
        return userRepository.findAllByRole(UserRole.DRIVER).stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public User getDriverById(Long id) {
        return userRepository.findByIdAndRole(id, UserRole.DISPATCHER)
                .orElseThrow(() -> new ResourceNotFoundException("Водитель с ID " + id + " не найден"));
    }

    public UserDto getDriverByIdDto(Long id) {
        return userMapper.toDto(getDriverById(id));
    }
}
