package com.danilscheglov.transportation.service;

import com.danilscheglov.transportation.dto.CargoDto;
import com.danilscheglov.transportation.entity.Cargo;
import com.danilscheglov.transportation.repository.CargoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CargoService {
    private final CargoRepository cargoRepository;

    @Transactional(readOnly = true)
    public List<CargoDto> getAllCargos() {
        return cargoRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CargoDto getCargoById(Long id) {
        return cargoRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new EntityNotFoundException("Груз с ID " + id + " не найден"));
    }

    @Transactional
    public CargoDto createCargo(CargoDto cargoDto) {
        Cargo cargo = convertToEntity(cargoDto);
        Cargo savedCargo = cargoRepository.save(cargo);
        return convertToDto(savedCargo);
    }

    @Transactional
    public CargoDto updateCargo(Long id, CargoDto cargoDto) {
        Cargo existingCargo = cargoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Груз с ID " + id + " не найден"));

        updateCargoFromDto(existingCargo, cargoDto);
        Cargo updatedCargo = cargoRepository.save(existingCargo);
        return convertToDto(updatedCargo);
    }

    @Transactional
    public void deleteCargo(Long id) {
        if (!cargoRepository.existsById(id)) {
            throw new EntityNotFoundException("Груз с ID " + id + " не найден");
        }
        cargoRepository.deleteById(id);
    }

    private CargoDto convertToDto(Cargo cargo) {
        return CargoDto.builder()
                .id(cargo.getId())
                .orderId(cargo.getOrder().getId())
                .type(cargo.getType())
                .weight(cargo.getWeight())
                .volume(cargo.getVolume())
                .build();
    }

    private Cargo convertToEntity(CargoDto cargoDto) {
        return Cargo.builder()
                .id(cargoDto.getId())
                .type(cargoDto.getType())
                .weight(cargoDto.getWeight())
                .volume(cargoDto.getVolume())
                .build();
    }

    private void updateCargoFromDto(Cargo cargo, CargoDto cargoDto) {
        cargo.setType(cargoDto.getType());
        cargo.setWeight(cargoDto.getWeight());
        cargo.setVolume(cargoDto.getVolume());
    }
}