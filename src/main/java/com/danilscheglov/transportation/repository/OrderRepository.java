package com.danilscheglov.transportation.repository;

import com.danilscheglov.transportation.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByClientEmail(String email);
}