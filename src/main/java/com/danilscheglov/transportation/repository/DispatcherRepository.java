package com.danilscheglov.transportation.repository;

import com.danilscheglov.transportation.entity.Dispatcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DispatcherRepository extends JpaRepository<Dispatcher, Long> {

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}