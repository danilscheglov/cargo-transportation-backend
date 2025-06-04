package com.danilscheglov.transportation.service;

import com.danilscheglov.transportation.dto.MaintenanceRequestDTO;
import com.danilscheglov.transportation.entity.Car;
import com.danilscheglov.transportation.entity.MaintenanceRequest;
import com.danilscheglov.transportation.entity.User;
import com.danilscheglov.transportation.entity.common.CarCondition;
import com.danilscheglov.transportation.entity.common.MaintenanceStatus;
import com.danilscheglov.transportation.mapper.MaintenanceRequestMapper;
import com.danilscheglov.transportation.repository.CarRepository;
import com.danilscheglov.transportation.repository.MaintenanceRequestRepository;
import com.danilscheglov.transportation.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MaintenanceRequestService {

    private final MaintenanceRequestRepository maintenanceRequestRepository;
    private final CarRepository carRepository;
    private final UserRepository UserRepository;

    private final MaintenanceRequestMapper maintenanceRequestMapper;

    @Transactional(readOnly = true)
    public List<MaintenanceRequestDTO> getAllMaintenanceRequests() {
        return maintenanceRequestRepository.findAll().stream()
                .map(maintenanceRequestMapper::toMaintenanceRequestDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MaintenanceRequestDTO getMaintenanceRequestById(Long id) {
        return maintenanceRequestRepository.findById(id)
                .map(maintenanceRequestMapper::toMaintenanceRequestDTO)
                .orElseThrow(() -> new EntityNotFoundException("Заявка с ID " + id + " не найдена"));
    }

    @Transactional
    public MaintenanceRequestDTO createMaintenanceRequest(MaintenanceRequestDTO maintenanceRequestDTO) {
        MaintenanceRequest maintenanceRequest = maintenanceRequestMapper.toMaintenanceRequest(maintenanceRequestDTO);
        return maintenanceRequestMapper.toMaintenanceRequestDTO(maintenanceRequestRepository.save(maintenanceRequest));
    }

    @Transactional
    public MaintenanceRequestDTO updateMaintenanceRequest(Long id, MaintenanceRequestDTO maintenanceRequestDTO) {
        MaintenanceRequest existingRequest = maintenanceRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Заявка с ID " + id + " не найдена"));
        updateEntityFromDTO(existingRequest, maintenanceRequestDTO);
        carRepository.save(existingRequest.getCar());
        return maintenanceRequestMapper.toMaintenanceRequestDTO(maintenanceRequestRepository.save(existingRequest));
    }

    @Transactional
    public void deleteMaintenanceRequest(Long id) {
        if (!maintenanceRequestRepository.existsById(id)) {
            throw new EntityNotFoundException("Заявка с ID " + id + " не найдена");
        }
        maintenanceRequestRepository.deleteById(id);
    }

    private void updateEntityFromDTO(MaintenanceRequest entity, MaintenanceRequestDTO dto) {
        if (dto.getCar() != null && Objects.equals(dto.getCar().getId(), entity.getCar().getId())) {
            Car car = carRepository.findById(dto.getCar().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Автомобиль с ID " + dto.getCar().getId() + " не найден"));
            if (dto.getStatus().equals(MaintenanceStatus.COMPLETED.getDisplayName())) {
                car.setCondition(CarCondition.OPERATIONAL.toString());
            }
            entity.setCar(car);
        }

        if (dto.getMechanic() != null && !dto.getMechanic().getId().equals(entity.getMechanic().getId())) {
            User mechanic = UserRepository.findById(dto.getMechanic().getId())
                    .orElseThrow(
                            () -> new EntityNotFoundException("Механик с ID " + dto.getMechanic().getId() + " не найден"));
            entity.setMechanic(mechanic);
        }

        entity.setCreatedAt(dto.getFillingDate());
        entity.setServiceType(dto.getServiceType());
        entity.setStatus(dto.getStatus());
        entity.setNote(dto.getNote());
    }
}
