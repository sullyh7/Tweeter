package com.sulay.tweeter.controller;

import com.sulay.tweeter.dto.ReplyRequest;
import com.sulay.tweeter.dto.ReplyResponse;
import com.sulay.tweeter.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/replies")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping("/{replyId}")
    public ResponseEntity<ReplyResponse> getReply(@PathVariable("replyId") Long replyId) {
        return ResponseEntity.ok().body(replyService.getReply(replyId));
    }

    @PostMapping
    public ResponseEntity<ReplyResponse> reply(@RequestBody ReplyRequest replyRequest) {
        ReplyResponse replyResponse = replyService.tweetReply(replyRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(replyResponse.getId())
                .toUri();
        return ResponseEntity.created(location).body(replyResponse);
    }

    @DeleteMapping("/{replyId}")
    public ResponseEntity<Object> deleteReply(@PathVariable("replyId") Long replyId) {
        replyService.deleteReply(replyId);
        return ResponseEntity.ok().build();
    }
}
