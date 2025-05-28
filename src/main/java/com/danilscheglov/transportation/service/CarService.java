package com.danilscheglov.transportation.service;

import com.danilscheglov.transportation.dto.CarDTO;
import com.danilscheglov.transportation.entity.Car;
import com.danilscheglov.transportation.entity.User;
import com.danilscheglov.transportation.exception.ResourceNotFoundException;
import com.danilscheglov.transportation.exception.UniqueConstraintViolationException;
import com.danilscheglov.transportation.mapper.CarMapper;
import com.danilscheglov.transportation.mapper.UserMapper;
import com.danilscheglov.transportation.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final DriverService driverService;

    private final CarMapper carMapper;
    private final UserMapper userMapper;

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car getCarById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Автомобиль с ID " + id + " не найден"));
    }

    @Transactional
    public Car createCar(CarDTO carDTO) {
        validateUniqueConstraints(carDTO);
        User driver = null;
        if (carDTO.getDriverId() != null) {
            driver = driverService.getDriverById(carDTO.getDriverId());
        }

        Car car = carMapper.toCar(carDTO, driver);

        return carRepository.save(car);
    }

    @Transactional
    public Car updateCar(Long id, CarDTO carDTO) {
        Car existingCar = getCarById(id);

        if (!existingCar.getNumber().equals(carDTO.getNumber())) {
            validateNumberUnique(carDTO.getNumber());
        }

        User driver = null;
        if (carDTO.getDriverId() != null) {
            driver = driverService.getDriverById(carDTO.getDriverId());
        }

        existingCar.setDriver(driver);
        existingCar.setNumber(carDTO.getNumber());
        existingCar.setModel(carDTO.getModel());
        existingCar.setBrand(carDTO.getBrand());
        existingCar.setCapacity(carDTO.getCapacity());
        existingCar.setMileage(carDTO.getMileage());
        existingCar.setCondition(carDTO.getCondition());
        existingCar.setLastMaintenanceDate(carDTO.getLastMaintenanceDate());

        return carRepository.save(existingCar);
    }

    @Transactional
    public void deleteCar(Long id) {
        if (!carRepository.existsById(id)) {
            throw new ResourceNotFoundException("Автомобиль с ID " + id + " не найден");
        }
        carRepository.deleteById(id);
    }

    private void validateUniqueConstraints(CarDTO carDTO) {
        validateNumberUnique(carDTO.getNumber());
    }

    private void validateNumberUnique(String number) {
        if (carRepository.existsByNumber(number)) {
            throw new UniqueConstraintViolationException("Автомобиль с номером " + number + " уже существует");
        }
    }
}
