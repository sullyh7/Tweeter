package com.sulay.tweeter.controller;

import com.sulay.tweeter.dto.ReplyResponse;
import com.sulay.tweeter.dto.TweetResponse;
import com.sulay.tweeter.dto.UserDto;
import com.sulay.tweeter.service.ReplyService;
import com.sulay.tweeter.service.TweetService;
import com.sulay.tweeter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users/currentUser")
@RequiredArgsConstructor
public class CurrentUserController {

    private final UserService userService;
    private final TweetService tweetService;
    private final ReplyService replyService;

    @GetMapping
    public ResponseEntity<UserDto> getCurrentUser() {
        return ResponseEntity.ok().body(userService.getCurrentUser());
    }

    @GetMapping("/followers")
    public ResponseEntity<List<UserDto>> getCurrentUserFollowers() {
        return ResponseEntity.ok().body(userService.getCurrentUserFollowers());
    }

    @GetMapping("/tweets")
    public ResponseEntity<List<TweetResponse>> getCurrentUserTweets() {
        return ResponseEntity.ok().body(tweetService.getCurrentUserTweets());
    }

    @GetMapping("/replies")
    public ResponseEntity<List<ReplyResponse>> getCurrentUserReplies() {
        return ResponseEntity.ok().body(replyService.getCurrentUserReplies());
    }
}
