package com.danilscheglov.transportation.service;

import com.danilscheglov.transportation.dto.FlightDTO;
import com.danilscheglov.transportation.entity.Car;
import com.danilscheglov.transportation.entity.Dispatcher;
import com.danilscheglov.transportation.entity.Driver;
import com.danilscheglov.transportation.entity.Flight;
import com.danilscheglov.transportation.exception.ResourceNotFoundException;
import com.danilscheglov.transportation.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;
    private final DriverService driverService;
    private final DispatcherService dispatcherService;
    private final CarService carService;

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public Flight getFlightById(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Рейс с ID " + id + " не найден"));
    }

    @Transactional
    public Flight createFlight(FlightDTO flightDTO) {
        Driver driver = driverService.getDriverById(flightDTO.getDriverId());
        Dispatcher dispatcher = dispatcherService.getDispatcherById(flightDTO.getDispatcherId());
        Car car = carService.getCarById(flightDTO.getCarId());

        Flight flight = Flight.builder()
                .driver(driver)
                .dispatcher(dispatcher)
                .car(car)
                .startPoint(flightDTO.getStartPoint())
                .endPoint(flightDTO.getEndPoint())
                .distance(flightDTO.getDistance())
                .departureDate(flightDTO.getDepartureDate())
                .build();

        return flightRepository.save(flight);
    }

    @Transactional
    public Flight updateFlight(Long id, FlightDTO flightDTO) {
        Flight existingFlight = getFlightById(id);
        Driver driver = driverService.getDriverById(flightDTO.getDriverId());
        Dispatcher dispatcher = dispatcherService.getDispatcherById(flightDTO.getDispatcherId());
        Car car = carService.getCarById(flightDTO.getCarId());

        existingFlight.setDriver(driver);
        existingFlight.setDispatcher(dispatcher);
        existingFlight.setCar(car);
        existingFlight.setStartPoint(flightDTO.getStartPoint());
        existingFlight.setEndPoint(flightDTO.getEndPoint());
        existingFlight.setDistance(flightDTO.getDistance());
        existingFlight.setDepartureDate(flightDTO.getDepartureDate());

        return flightRepository.save(existingFlight);
    }

    @Transactional
    public void deleteFlight(Long id) {
        if (!flightRepository.existsById(id)) {
            throw new ResourceNotFoundException("Рейс с ID " + id + " не найден");
        }
        flightRepository.deleteById(id);
    }
}