package com.sulay.tweeter.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private final String SECRET_KEY = "secret";
//    private final long JWT_EXPIRY = 3_600_000L;
    private final long JWT_EXPIRY = 900000; // 15 minutes

    public String generateJwtFromUser(User user) {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(Date.from(Instant.now().plusMillis(JWT_EXPIRY)))
                .withIssuer("Tweeter")
                .sign(algorithm);
    }


    public DecodedJWT decodeAndVerifyAccessToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    public long getJwtExpiry() {
        return this.JWT_EXPIRY;
    }

    public String generateJwtFromUsername(String username) {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(Date.from(Instant.now().plusMillis(JWT_EXPIRY)))
                .withIssuer("Tweeter")
                .sign(algorithm);
    }
}
