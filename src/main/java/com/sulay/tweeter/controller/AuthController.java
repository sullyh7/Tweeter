package com.sulay.tweeter.controller;

import com.sulay.tweeter.dto.LoginRequest;
import com.sulay.tweeter.dto.LoginResponse;
import com.sulay.tweeter.dto.RefreshTokenRequest;
import com.sulay.tweeter.dto.RegisterRequest;
import com.sulay.tweeter.security.RefreshTokenService;
import com.sulay.tweeter.service.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody RegisterRequest registerRequest) {
        this.authService.signup(registerRequest);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        log.info("Logging in...");
        return authService.login(loginRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.ok().body("Logged out successfully");
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<Object> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok().body(authService.refreshToken(refreshTokenRequest));
    }


    @GetMapping("/verify/{verificationToken}")
    public ResponseEntity<String> verify(@PathVariable("verificationToken") String verificationToken) {
        this.authService.verify(verificationToken);
        return new ResponseEntity<>("Successfully activated", HttpStatus.OK);
    }
}
