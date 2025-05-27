package com.danilscheglov.transportation.repository;

import com.danilscheglov.transportation.entity.Mechanic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MechanicRepository extends JpaRepository<Mechanic, Long> {

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}