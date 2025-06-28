package com.Placement.PlacementTracker.authentication.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    Boolean existsByRollNumber(String rollNumber);

    Boolean existsByEmail(String email);
}
