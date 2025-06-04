package com.danilscheglov.transportation.repository;

import com.danilscheglov.transportation.entity.MaintenanceRequest;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceRequestRepository extends JpaRepository<MaintenanceRequest, Long> {

    @EntityGraph(attributePaths = {"mechanic"})
    List<MaintenanceRequest> findAll();

}
