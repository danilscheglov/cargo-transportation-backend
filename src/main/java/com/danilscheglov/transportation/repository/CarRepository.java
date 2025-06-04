package com.danilscheglov.transportation.repository;

import com.danilscheglov.transportation.entity.Car;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @EntityGraph(attributePaths = {"driver"})
    List<Car> findAll();

    boolean existsByNumber(String number);
}
