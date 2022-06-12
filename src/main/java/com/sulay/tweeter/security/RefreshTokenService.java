package com.sulay.tweeter.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sulay.tweeter.entity.RefreshToken;
import com.sulay.tweeter.exception.TweeterException;
import com.sulay.tweeter.repostiory.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final long REFRESH_TOKEN_EXPIRY =  604800000;
    private final RefreshTokenRepository refreshTokenRepository;

//    public String generateRefreshTokenFromUser(User user) {
//        Algorithm algorithm = Algorithm.HMAC256("secret");
//        return JWT.create()
//                .withSubject(user.getUsername())
//                .withExpiresAt(Date.from(Instant.now().plusMillis(REFRESH_TOKEN_EXPIRY)))
//                .withIssuer("Tweeter")
//                .withClaim("roles", user.getAuthorities().stream()
//                        .map(GrantedAuthority::getAuthority)
//                        .collect(Collectors.toList()))
//                .sign(algorithm);
//    }
    @Transactional
    public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());

        return refreshTokenRepository.save(refreshToken);
    }

    @Transactional(readOnly = true)
    public void validateRefreshToken(String token) {
        refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new TweeterException("Invalid refresh token"));
    }

    @Transactional
    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

}
