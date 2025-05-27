package com.danilscheglov.transportation.service;

import com.danilscheglov.transportation.dto.DriverDTO;
import com.danilscheglov.transportation.entity.Driver;
import com.danilscheglov.transportation.exception.ResourceNotFoundException;
import com.danilscheglov.transportation.exception.UniqueConstraintViolationException;
import com.danilscheglov.transportation.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverService {
    private final DriverRepository driverRepository;

    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    public Driver getDriverById(Long id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Водитель с ID " + id + " не найден"));
    }

    @Transactional
    public Driver createDriver(DriverDTO driverDTO) {
        validateUniqueConstraints(driverDTO);

        Driver driver = Driver.builder()
                .surname(driverDTO.getSurname())
                .name(driverDTO.getName())
                .patronymic(driverDTO.getPatronymic())
                .phone(driverDTO.getPhone())
                .email(driverDTO.getEmail())
                .password(driverDTO.getPassword())
                .build();

        return driverRepository.save(driver);
    }

    @Transactional
    public Driver updateDriver(Long id, DriverDTO driverDTO) {
        Driver existingDriver = getDriverById(id);

        if (!existingDriver.getEmail().equals(driverDTO.getEmail())) {
            validateEmailUnique(driverDTO.getEmail());
        }
        if (!existingDriver.getPhone().equals(driverDTO.getPhone())) {
            validatePhoneUnique(driverDTO.getPhone());
        }

        existingDriver.setSurname(driverDTO.getSurname());
        existingDriver.setName(driverDTO.getName());
        existingDriver.setPatronymic(driverDTO.getPatronymic());
        existingDriver.setPhone(driverDTO.getPhone());
        existingDriver.setEmail(driverDTO.getEmail());
        if (driverDTO.getPassword() != null && !driverDTO.getPassword().isEmpty()) {
            existingDriver.setPassword(driverDTO.getPassword());
        }

        return driverRepository.save(existingDriver);
    }

    @Transactional
    public void deleteDriver(Long id) {
        if (!driverRepository.existsById(id)) {
            throw new ResourceNotFoundException("Водитель с ID " + id + " не найден");
        }
        driverRepository.deleteById(id);
    }

    private void validateUniqueConstraints(DriverDTO driverDTO) {
        validateEmailUnique(driverDTO.getEmail());
        validatePhoneUnique(driverDTO.getPhone());
    }

    private void validateEmailUnique(String email) {
        if (driverRepository.existsByEmail(email)) {
            throw new UniqueConstraintViolationException("Водитель с email " + email + " уже существует");
        }
    }

    private void validatePhoneUnique(String phone) {
        if (driverRepository.existsByPhone(phone)) {
            throw new UniqueConstraintViolationException("Водитель с телефоном " + phone + " уже существует");
        }
    }
}