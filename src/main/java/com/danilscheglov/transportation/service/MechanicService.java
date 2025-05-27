package com.danilscheglov.transportation.service;

import com.danilscheglov.transportation.dto.MechanicDTO;
import com.danilscheglov.transportation.entity.Mechanic;
import com.danilscheglov.transportation.repository.MechanicRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MechanicService {
    private final MechanicRepository mechanicRepository;

    @Transactional(readOnly = true)
    public List<MechanicDTO> getAllMechanics() {
        return mechanicRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MechanicDTO getMechanicById(Long id) {
        return mechanicRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Механик с ID " + id + " не найден"));
    }

    @Transactional
    public MechanicDTO createMechanic(MechanicDTO mechanicDTO) {
        validateUniqueFields(mechanicDTO);
        Mechanic mechanic = convertToEntity(mechanicDTO);
        return convertToDTO(mechanicRepository.save(mechanic));
    }

    @Transactional
    public MechanicDTO updateMechanic(Long id, MechanicDTO mechanicDTO) {
        Mechanic existingMechanic = mechanicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Механик с ID " + id + " не найден"));

        if (!existingMechanic.getEmail().equals(mechanicDTO.getEmail())) {
            validateEmail(mechanicDTO.getEmail());
        }
        if (!existingMechanic.getPhone().equals(mechanicDTO.getPhone())) {
            validatePhone(mechanicDTO.getPhone());
        }

        existingMechanic.setSurname(mechanicDTO.getSurname());
        existingMechanic.setName(mechanicDTO.getName());
        existingMechanic.setPatronymic(mechanicDTO.getPatronymic());
        existingMechanic.setPhone(mechanicDTO.getPhone());
        existingMechanic.setEmail(mechanicDTO.getEmail());

        if (mechanicDTO.getPassword() != null && !mechanicDTO.getPassword().isEmpty()) {
            existingMechanic.setPassword(mechanicDTO.getPassword());
        }

        return convertToDTO(mechanicRepository.save(existingMechanic));
    }

    @Transactional
    public void deleteMechanic(Long id) {
        if (!mechanicRepository.existsById(id)) {
            throw new EntityNotFoundException("Механик с ID " + id + " не найден");
        }
        mechanicRepository.deleteById(id);
    }

    private void validateUniqueFields(MechanicDTO mechanicDTO) {
        validateEmail(mechanicDTO.getEmail());
        validatePhone(mechanicDTO.getPhone());
    }

    private void validateEmail(String email) {
        if (mechanicRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email " + email + " уже используется");
        }
    }

    private void validatePhone(String phone) {
        if (mechanicRepository.existsByPhone(phone)) {
            throw new IllegalArgumentException("Телефон " + phone + " уже используется");
        }
    }

    private MechanicDTO convertToDTO(Mechanic mechanic) {
        return MechanicDTO.builder()
                .id(mechanic.getId())
                .surname(mechanic.getSurname())
                .name(mechanic.getName())
                .patronymic(mechanic.getPatronymic())
                .phone(mechanic.getPhone())
                .email(mechanic.getEmail())
                .build();
    }

    private Mechanic convertToEntity(MechanicDTO dto) {
        return Mechanic.builder()
                .id(dto.getId())
                .surname(dto.getSurname())
                .name(dto.getName())
                .patronymic(dto.getPatronymic())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }
}