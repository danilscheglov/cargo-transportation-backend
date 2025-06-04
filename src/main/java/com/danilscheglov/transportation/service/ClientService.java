package com.danilscheglov.transportation.service;

import com.danilscheglov.transportation.dto.UserDto;
import com.danilscheglov.transportation.entity.User;
import com.danilscheglov.transportation.entity.common.UserRole;
import com.danilscheglov.transportation.exception.ResourceNotFoundException;
import com.danilscheglov.transportation.mapper.UserMapper;
import com.danilscheglov.transportation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final UserRepository clientRepository;
    private final UserMapper userMapper;

    public List<User> getAllClients() {
        return clientRepository.findAllByRole(UserRole.CLIENT);
    }

    public User getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Клиент с ID " + id + " не найден"));
    }

    public User getClientByEmail(String email) {
        return clientRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Клиент с email " + email + " не найден"));
    }

    @Transactional
    public User createClient(UserDto clientDTO) {
        return clientRepository.save(userMapper.fromDto(clientDTO));
    }

    @Transactional
    public User updateClient(String email, UserDto clientDTO) {
        User existingClient = getClientByEmail(email);
        existingClient.setSurname(clientDTO.getSurname());
        existingClient.setName(clientDTO.getName());
        existingClient.setPatronymic(clientDTO.getPatronymic());
        existingClient.setPhone(clientDTO.getPhone());
        existingClient.setEmail(clientDTO.getEmail());
        if (clientDTO.getPassword() != null && !clientDTO.getPassword().isEmpty()) {
            existingClient.setPassword(clientDTO.getPassword());
        }
        return clientRepository.save(existingClient);
    }

    @Transactional
    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Клиент с ID " + id + " не найден");
        }
        clientRepository.deleteById(id);
    }
}
