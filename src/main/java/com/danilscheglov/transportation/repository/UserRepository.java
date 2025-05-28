package com.danilscheglov.transportation.repository;

import com.danilscheglov.transportation.entity.User;
import com.danilscheglov.transportation.entity.common.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByRole(UserRole role);

    Optional<User> findByIdAndRole(Long id, UserRole role);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}
