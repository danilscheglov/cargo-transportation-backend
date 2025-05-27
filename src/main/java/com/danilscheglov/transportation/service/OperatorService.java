package com.danilscheglov.transportation.service;

import com.danilscheglov.transportation.dto.OperatorDTO;
import com.danilscheglov.transportation.entity.Operator;
import com.danilscheglov.transportation.repository.OperatorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OperatorService {
    private final OperatorRepository operatorRepository;

    @Transactional(readOnly = true)
    public List<OperatorDTO> getAllOperators() {
        return operatorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OperatorDTO getOperatorById(Long id) {
        return operatorRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Оператор с ID " + id + " не найден"));
    }

    @Transactional
    public OperatorDTO createOperator(OperatorDTO operatorDTO) {
        validateUniqueFields(operatorDTO);
        Operator operator = convertToEntity(operatorDTO);
        return convertToDTO(operatorRepository.save(operator));
    }

    @Transactional
    public OperatorDTO updateOperator(Long id, OperatorDTO operatorDTO) {
        Operator existingOperator = operatorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Оператор с ID " + id + " не найден"));

        if (!existingOperator.getEmail().equals(operatorDTO.getEmail())) {
            validateEmail(operatorDTO.getEmail());
        }
        if (!existingOperator.getPhone().equals(operatorDTO.getPhone())) {
            validatePhone(operatorDTO.getPhone());
        }

        existingOperator.setSurname(operatorDTO.getSurname());
        existingOperator.setName(operatorDTO.getName());
        existingOperator.setPatronymic(operatorDTO.getPatronymic());
        existingOperator.setPhone(operatorDTO.getPhone());
        existingOperator.setEmail(operatorDTO.getEmail());

        if (operatorDTO.getPassword() != null && !operatorDTO.getPassword().isEmpty()) {
            existingOperator.setPassword(operatorDTO.getPassword());
        }

        return convertToDTO(operatorRepository.save(existingOperator));
    }

    @Transactional
    public void deleteOperator(Long id) {
        if (!operatorRepository.existsById(id)) {
            throw new EntityNotFoundException("Оператор с ID " + id + " не найден");
        }
        operatorRepository.deleteById(id);
    }

    private void validateUniqueFields(OperatorDTO operatorDTO) {
        validateEmail(operatorDTO.getEmail());
        validatePhone(operatorDTO.getPhone());
    }

    private void validateEmail(String email) {
        if (operatorRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email " + email + " уже используется");
        }
    }

    private void validatePhone(String phone) {
        if (operatorRepository.existsByPhone(phone)) {
            throw new IllegalArgumentException("Телефон " + phone + " уже используется");
        }
    }

    private OperatorDTO convertToDTO(Operator operator) {
        return OperatorDTO.builder()
                .id(operator.getId())
                .surname(operator.getSurname())
                .name(operator.getName())
                .patronymic(operator.getPatronymic())
                .phone(operator.getPhone())
                .email(operator.getEmail())
                .build();
    }

    private Operator convertToEntity(OperatorDTO dto) {
        return Operator.builder()
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