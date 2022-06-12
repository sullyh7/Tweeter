package com.sulay.tweeter.service;

import java.time.Instant;
import java.util.UUID;

import com.sulay.tweeter.dto.*;
import com.sulay.tweeter.entity.User;
import com.sulay.tweeter.exception.UserExistsException;
import com.sulay.tweeter.repostiory.UserRepository;
import com.sulay.tweeter.repostiory.VerificationTokenRepository;

import com.sulay.tweeter.security.JwtService;
import com.sulay.tweeter.security.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {


    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @Async
    public void signup(RegisterRequest registerRequest) {
        if (!userRepository.existsByUsername(registerRequest.getUsername())) {
            User user = new User();
            user.setUsername(registerRequest.getUsername());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setEmail(registerRequest.getEmail());
            user.setBiography(registerRequest.getBiography());
            user.setProfileImageUrl(registerRequest.getProfileImageUrl());
            user.setCreationDate(Instant.now());
            user.setEnabled(false);
            userRepository.save(user);
            VerificationToken token = generateToken(user);
            log.info("Token: " + token.getToken());
            this.emailService.sendVerificationMessage(user.getEmail(), token.getToken());
        } else {
            throw new UserExistsException("User with username " + registerRequest.getUsername() + " already exists");
        }
    }

    @Transactional
    public VerificationToken generateToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(user);
        verificationToken.setToken(token);
        this.verificationTokenRepository.save(verificationToken);

        return verificationToken;
    }

    @Transactional
    public void verify(String token) {
        VerificationToken verificationToken = this.verificationTokenRepository.findByToken(token).orElseThrow(() -> 
            new IllegalArgumentException("Invalid token"));
        User user = verificationToken.getUser();
        user.setEnabled(true);
        this.userRepository.save(user);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        org.springframework.security.core.userdetails.User user =
                (org.springframework.security.core.userdetails.User) authenticate.getPrincipal();

        String accessToken = jwtService.generateJwtFromUser(user);
        String refreshToken = refreshTokenService.generateRefreshToken().getToken();

        log.info("Access token generated: " + accessToken);
        log.info("Refresh token generated: " + refreshToken);

        return LoginResponse.builder()
                .authenticationToken(accessToken)
                .refreshToken(refreshToken)
                .username(user.getUsername())
                .expiresAt(Instant.now().plusMillis(jwtService.getJwtExpiry()))
                .build();
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        String auth = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(auth).orElseThrow(() -> new UsernameNotFoundException(
                "No user with username " + auth
        ));
    }

    public String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public LoginResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtService.generateJwtFromUsername(refreshTokenRequest.getUsername());
        return LoginResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtService.getJwtExpiry()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }
}