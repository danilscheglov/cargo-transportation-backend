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
public class DispatcherService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> getAllDispatchers() {
        return userRepository.findAllByRole(UserRole.DISPATCHER).stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public User getDispatcherById(Long id) {
        return userRepository.findByIdAndRole(id, UserRole.DISPATCHER)
                .orElseThrow(() -> new ResourceNotFoundException("Диспетчер с ID " + id + " не найден"));
    }

    public UserDto getDispatcherByIdDto(Long id) {
        return userMapper.toDto(getDispatcherById(id));
    }
}
