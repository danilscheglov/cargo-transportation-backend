package com.danilscheglov.transportation.service;

import com.danilscheglov.transportation.dto.OrderDTO;
import com.danilscheglov.transportation.entity.Car;
import com.danilscheglov.transportation.entity.Cargo;
import com.danilscheglov.transportation.entity.Order;
import com.danilscheglov.transportation.entity.User;
import com.danilscheglov.transportation.mapper.CargoMapper;
import com.danilscheglov.transportation.mapper.OrderMapper;
import com.danilscheglov.transportation.repository.CarRepository;
import com.danilscheglov.transportation.repository.OrderRepository;
import com.danilscheglov.transportation.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;

    private final OrderMapper orderMapper;
    private final CargoMapper cargoMapper;

    @Transactional(readOnly = true)
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(orderMapper::toOrderDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderDTO getOrderById(Long id) {
        return orderRepository.findById(id).map(orderMapper::toOrderDTO).orElseThrow(() -> new EntityNotFoundException("Заказ с ID " + id + " не найден"));
    }

    @Transactional
    public OrderDTO createOrder(OrderDTO orderDto) {
        Order order = orderMapper.toOrder(orderDto);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toOrderDTO(savedOrder);
    }

    @Transactional
    public OrderDTO updateOrder(Long id, OrderDTO orderDto) {
        Order existingOrder = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Заказ с ID " + id + " не найден"));
        updateOrderFromDto(existingOrder, orderDto);
        if (existingOrder.getCar() != null) {
            carRepository.save(existingOrder.getCar());
        }
        Order updatedOrder = orderRepository.save(existingOrder);
        return orderMapper.toOrderDTO(updatedOrder);
    }

    @Transactional
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new EntityNotFoundException("Заказ с ID " + id + " не найден");
        }
        orderRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> getOrderByEmail(String email) {
        return orderRepository.findAllByClientEmail(email)
                .stream()
                .map(orderMapper::toOrderDTO)
                .collect(Collectors.toList());
    }

    private void updateOrderFromDto(Order order, OrderDTO dto) {
        order.setStartpoint(dto.getStartpoint());
        order.setEndpoint(dto.getEndpoint());
        order.setStatus(dto.getStatus());

        Car car = null;
        User driver = null;

        Long carId = dto.getCar().getId();
        Long driverId = dto.getCar().getDriverId();

        if (carId != null && carId > 0) {
            car = carRepository.findById(carId)
                    .orElseThrow(() -> new EntityNotFoundException("Машина с ID " + carId + " не найдена"));
            if (driverId != null) {
                if (driverId > 0) {
                    driver = userRepository.findById(driverId).orElseThrow(() -> new EntityNotFoundException("Водитель с ID " + driverId + " не найдена"));
                }
                order.setDriver(driver);
                car.setDriver(driver);
            }
        }

        order.setCar(car);

        if (dto.getCargo() != null) {
            Cargo cargo = cargoMapper.fromDto(dto.getCargo());
            order.setCargo(cargo);
        }
    }
}
