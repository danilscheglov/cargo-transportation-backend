package com.danilscheglov.transportation.service;

import com.danilscheglov.transportation.dto.DispatcherDTO;
import com.danilscheglov.transportation.entity.Dispatcher;
import com.danilscheglov.transportation.exception.ResourceNotFoundException;
import com.danilscheglov.transportation.exception.ValidationException;
import com.danilscheglov.transportation.repository.DispatcherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DispatcherService {
    private final DispatcherRepository dispatcherRepository;

    public List<Dispatcher> getAllDispatchers() {
        return dispatcherRepository.findAll();
    }

    public Dispatcher getDispatcherById(Long id) {
        return dispatcherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Диспетчер с ID " + id + " не найден"));
    }

    @Transactional
    public Dispatcher createDispatcher(DispatcherDTO dispatcherDTO) {
        validateUniqueFields(dispatcherDTO.getEmail(), dispatcherDTO.getPhone());

        Dispatcher dispatcher = Dispatcher.builder()
                .surname(dispatcherDTO.getSurname())
                .name(dispatcherDTO.getName())
                .patronymic(dispatcherDTO.getPatronymic())
                .phone(dispatcherDTO.getPhone())
                .email(dispatcherDTO.getEmail())
                .password(dispatcherDTO.getPassword())
                .build();

        return dispatcherRepository.save(dispatcher);
    }

    @Transactional
    public Dispatcher updateDispatcher(Long id, DispatcherDTO dispatcherDTO) {
        Dispatcher existingDispatcher = getDispatcherById(id);

        if (!existingDispatcher.getEmail().equals(dispatcherDTO.getEmail())) {
            validateUniqueEmail(dispatcherDTO.getEmail());
        }
        if (!existingDispatcher.getPhone().equals(dispatcherDTO.getPhone())) {
            validateUniquePhone(dispatcherDTO.getPhone());
        }

        existingDispatcher.setSurname(dispatcherDTO.getSurname());
        existingDispatcher.setName(dispatcherDTO.getName());
        existingDispatcher.setPatronymic(dispatcherDTO.getPatronymic());
        existingDispatcher.setPhone(dispatcherDTO.getPhone());
        existingDispatcher.setEmail(dispatcherDTO.getEmail());

        if (dispatcherDTO.getPassword() != null && !dispatcherDTO.getPassword().isEmpty()) {
            existingDispatcher.setPassword(dispatcherDTO.getPassword());
        }

        return dispatcherRepository.save(existingDispatcher);
    }

    @Transactional
    public void deleteDispatcher(Long id) {
        if (!dispatcherRepository.existsById(id)) {
            throw new ResourceNotFoundException("Диспетчер с ID " + id + " не найден");
        }
        dispatcherRepository.deleteById(id);
    }

    private void validateUniqueFields(String email, String phone) {
        validateUniqueEmail(email);
        validateUniquePhone(phone);
    }

    private void validateUniqueEmail(String email) {
        if (dispatcherRepository.existsByEmail(email)) {
            throw new ValidationException("Диспетчер с email " + email + " уже существует");
        }
    }

    private void validateUniquePhone(String phone) {
        if (dispatcherRepository.existsByPhone(phone)) {
            throw new ValidationException("Диспетчер с телефоном " + phone + " уже существует");
        }
    }
}