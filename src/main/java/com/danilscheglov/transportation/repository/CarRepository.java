package com.danilscheglov.transportation.repository;

import com.danilscheglov.transportation.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    boolean existsByNumber(String number);
}