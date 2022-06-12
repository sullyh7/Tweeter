package com.sulay.tweeter.controller;

import com.sulay.tweeter.dto.TweetResponse;
import com.sulay.tweeter.dto.UserDto;
import com.sulay.tweeter.service.FollowService;
import com.sulay.tweeter.service.TweetService;
import com.sulay.tweeter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final TweetService tweetService;
    private final FollowService followService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable("userId") Long id) {
        return ResponseEntity.ok().body(userService.getUser(id));
    }

    @GetMapping("/{userId}/tweets")
    public ResponseEntity<List<TweetResponse>> getUserTweets(@PathVariable Long userId) {
        return ResponseEntity.ok().body(tweetService.getUserTweets(userId));
    }

    @GetMapping("/{userId}/followers")
    public ResponseEntity<List<UserDto>> getFollowers(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok().body(userService.getFollowers(userId));
    }

    @GetMapping("/{userId}/following")
    public ResponseEntity<List<UserDto>> getFollowing(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok().body(userService.getFollowing(userId));
    }

}
