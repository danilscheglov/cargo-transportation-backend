package com.danilscheglov.transportation.service;

import com.danilscheglov.transportation.dto.OrderDTO;
import com.danilscheglov.transportation.entity.Order;
import com.danilscheglov.transportation.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderDTO getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new EntityNotFoundException("Заказ с ID " + id + " не найден"));
    }

    @Transactional
    public OrderDTO createOrder(OrderDTO orderDto) {
        Order order = convertToEntity(orderDto);
        Order savedOrder = orderRepository.save(order);
        return convertToDto(savedOrder);
    }

    @Transactional
    public OrderDTO updateOrder(Long id, OrderDTO orderDto) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Заказ с ID " + id + " не найден"));

        updateOrderFromDto(existingOrder, orderDto);
        Order updatedOrder = orderRepository.save(existingOrder);
        return convertToDto(updatedOrder);
    }

    @Transactional
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new EntityNotFoundException("Заказ с ID " + id + " не найден");
        }
        orderRepository.deleteById(id);
    }

    private OrderDTO convertToDto(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .clientId(order.getClient().getId())
                .operatorId(order.getOperator().getId())
                .flightId(order.getFlight() != null ? order.getFlight().getId() : null)
                .startpoint(order.getStartpoint())
                .endpoint(order.getEndpoint())
                .dispatchDate(order.getDispatchDate())
                .deliveryDate(order.getDeliveryDate())
                .status(order.getStatus())
                .build();
    }

    private Order convertToEntity(OrderDTO orderDto) {
        return Order.builder()
                .id(orderDto.getId())
                .startpoint(orderDto.getStartpoint())
                .endpoint(orderDto.getEndpoint())
                .dispatchDate(orderDto.getDispatchDate())
                .deliveryDate(orderDto.getDeliveryDate())
                .status(orderDto.getStatus())
                .build();
    }

    private void updateOrderFromDto(Order order, OrderDTO orderDto) {
        order.setStartpoint(orderDto.getStartpoint());
        order.setEndpoint(orderDto.getEndpoint());
        order.setDispatchDate(orderDto.getDispatchDate());
        order.setDeliveryDate(orderDto.getDeliveryDate());
        order.setStatus(orderDto.getStatus());
    }
}