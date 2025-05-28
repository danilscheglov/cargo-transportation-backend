package com.danilscheglov.transportation.service;

import com.danilscheglov.transportation.dto.MaintenanceRequestDTO;
import com.danilscheglov.transportation.entity.Car;
import com.danilscheglov.transportation.entity.MaintenanceRequest;
import com.danilscheglov.transportation.entity.User;
import com.danilscheglov.transportation.repository.CarRepository;
import com.danilscheglov.transportation.repository.MaintenanceRequestRepository;
import com.danilscheglov.transportation.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MaintenanceRequestService {
    private final MaintenanceRequestRepository maintenanceRequestRepository;
    private final CarRepository carRepository;
    private final UserRepository UserRepository;

    @Transactional(readOnly = true)
    public List<MaintenanceRequestDTO> getAllMaintenanceRequests() {
        return maintenanceRequestRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MaintenanceRequestDTO getMaintenanceRequestById(Long id) {
        return maintenanceRequestRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Заявка с ID " + id + " не найдена"));
    }

    @Transactional
    public MaintenanceRequestDTO createMaintenanceRequest(MaintenanceRequestDTO maintenanceRequestDTO) {
        MaintenanceRequest maintenanceRequest = convertToEntity(maintenanceRequestDTO);
        return convertToDTO(maintenanceRequestRepository.save(maintenanceRequest));
    }

    @Transactional
    public MaintenanceRequestDTO updateMaintenanceRequest(Long id, MaintenanceRequestDTO maintenanceRequestDTO) {
        MaintenanceRequest existingRequest = maintenanceRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Заявка с ID " + id + " не найдена"));

        updateEntityFromDTO(existingRequest, maintenanceRequestDTO);
        return convertToDTO(maintenanceRequestRepository.save(existingRequest));
    }

    @Transactional
    public void deleteMaintenanceRequest(Long id) {
        if (!maintenanceRequestRepository.existsById(id)) {
            throw new EntityNotFoundException("Заявка с ID " + id + " не найдена");
        }
        maintenanceRequestRepository.deleteById(id);
    }

    private MaintenanceRequest convertToEntity(MaintenanceRequestDTO dto) {
        Car car = carRepository.findById(dto.getCarId())
                .orElseThrow(() -> new EntityNotFoundException("Автомобиль с ID " + dto.getCarId() + " не найден"));

        User User = UserRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Механик с ID " + dto.getUserId() + " не найден"));

        return MaintenanceRequest.builder()
                .id(dto.getId())
                .car(car)
                .User(User)
                .fillingDate(dto.getFillingDate())
                .serviceType(dto.getServiceType())
                .status(dto.getStatus())
                .note(dto.getNote())
                .build();
    }

    private MaintenanceRequestDTO convertToDTO(MaintenanceRequest entity) {
        return MaintenanceRequestDTO.builder()
                .id(entity.getId())
                .carId(entity.getCar().getId())
                .UserId(entity.getUser().getId())
                .fillingDate(entity.getFillingDate())
                .serviceType(entity.getServiceType())
                .status(entity.getStatus())
                .note(entity.getNote())
                .build();
    }

    private void updateEntityFromDTO(MaintenanceRequest entity, MaintenanceRequestDTO dto) {
        if (dto.getCarId() != null && !dto.getCarId().equals(entity.getCar().getId())) {
            Car car = carRepository.findById(dto.getCarId())
                    .orElseThrow(() -> new EntityNotFoundException("Автомобиль с ID " + dto.getCarId() + " не найден"));
            entity.setCar(car);
        }

        if (dto.getUserId() != null && !dto.getUserId().equals(entity.getUser().getId())) {
            User User = UserRepository.findById(dto.getUserId())
                    .orElseThrow(
                            () -> new EntityNotFoundException("Механик с ID " + dto.getUserId() + " не найден"));
            entity.setUser(User);
        }

        entity.setFillingDate(dto.getFillingDate());
        entity.setServiceType(dto.getServiceType());
        entity.setStatus(dto.getStatus());
        entity.setNote(dto.getNote());
    }
}
