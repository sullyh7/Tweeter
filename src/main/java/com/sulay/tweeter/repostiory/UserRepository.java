package com.sulay.tweeter.repostiory;

import java.util.Optional;

import com.sulay.tweeter.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
    
