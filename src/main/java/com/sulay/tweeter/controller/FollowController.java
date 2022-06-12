package com.sulay.tweeter.controller;

import com.sulay.tweeter.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/followers")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{userId}")
    public ResponseEntity<Object> followUser(@PathVariable("userId") Long userId) {
        followService.followUser(userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> unfollowUser(@PathVariable("userId") Long userId) {
        followService.unfollowUser(userId);
        return ResponseEntity.ok().build();
    }
}
