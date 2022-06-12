package com.sulay.tweeter.repostiory;

import java.util.Optional;

import com.sulay.tweeter.dto.VerificationToken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);
    
}
