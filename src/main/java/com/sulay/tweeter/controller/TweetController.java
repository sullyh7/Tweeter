package com.sulay.tweeter.controller;

import com.sulay.tweeter.dto.ReplyResponse;
import com.sulay.tweeter.dto.TweetRequest;
import com.sulay.tweeter.dto.TweetResponse;
import com.sulay.tweeter.service.AuthService;
import com.sulay.tweeter.service.ReplyService;
import com.sulay.tweeter.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tweets")
public class TweetController {
    private final TweetService tweetService;
    private final ReplyService replyService;
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<List<TweetResponse>> getTweets() {
        return ResponseEntity.ok(tweetService.getTweets());
    }

    @PostMapping
    public ResponseEntity<TweetResponse> tweet(@RequestBody TweetRequest tweetRequest) {
        TweetResponse tweetResponse = tweetService.tweet(tweetRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(tweetResponse.getId())
                .toUri();
        return ResponseEntity.created(location).body(tweetResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TweetResponse> getTweet(@PathVariable Long id) {
        return ResponseEntity.ok().body(tweetService.getTweet(id));
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<TweetResponse>> getTweetByKeyword(@PathVariable String keyword) {
        return ResponseEntity.ok().body(tweetService.getTweetsByKeyword(keyword));
    }

    @DeleteMapping("/{tweetId}")
    public ResponseEntity<String> removeTweet(@PathVariable("tweetId") Long tweetId) {
        tweetService.removeTweet(tweetId);
        return ResponseEntity.ok().body("Successfully removed tweet with id" + tweetId);
    }

    @GetMapping("/{tweetId}/replies")
    public ResponseEntity<List<ReplyResponse>> getTweetReplies(@PathVariable("tweetId") Long tweetId) {
         return ResponseEntity.ok().body(replyService.getTweetReplies(tweetId));
    }

}
