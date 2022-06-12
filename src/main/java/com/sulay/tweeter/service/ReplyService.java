package com.sulay.tweeter.service;

import com.sulay.tweeter.dto.ReplyRequest;
import com.sulay.tweeter.dto.ReplyResponse;
import com.sulay.tweeter.entity.Reply;
import com.sulay.tweeter.exception.ReplyNotFoundException;
import com.sulay.tweeter.exception.TweetNotFoundException;
import com.sulay.tweeter.exception.TweeterException;
import com.sulay.tweeter.mapper.ReplyMapper;
import com.sulay.tweeter.repostiory.ReplyRepository;
import com.sulay.tweeter.repostiory.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final ReplyMapper replyMapper;
    private final TweetRepository tweetRepository;
    private final AuthService authService;

    public List<ReplyResponse> getTweetReplies(Long tweetId) {
        tweetRepository.findById(tweetId).orElseThrow(
                () -> new TweetNotFoundException("No tweet with id " + tweetId)
        );
        return replyRepository.findAllByParentId(tweetId).stream()
                .map(replyMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ReplyResponse getReply(Long replyId) {
        return replyMapper.mapToResponse(replyRepository.findById(replyId).orElseThrow(
                () -> new ReplyNotFoundException("No reply with id " + replyId)
        ));
    }

    @Transactional
    public ReplyResponse tweetReply(ReplyRequest replyRequest) {
        tweetRepository.findById(replyRequest.getParentId()).orElseThrow(
                () -> new TweetNotFoundException("No tweet with id " + replyRequest.getParentId())
        );

        return replyMapper.mapToResponse(replyRepository.save(replyMapper.mapToReply(replyRequest, authService.getCurrentUser())));

    }

    @Transactional
    public void deleteReply(Long replyId) {
        Reply reply = replyRepository.findById(replyId).orElseThrow(() ->
                new ReplyNotFoundException("No reply with id " + replyId));
        if (reply.getAuthor().getUsername().equals(authService.getCurrentUser().getUsername())) {
            replyRepository.delete(reply);
        } else {
            throw new ReplyNotFoundException("No reply with id " + replyId + " posted by " +
                    authService.getCurrentUser().getUsername());
        }
    }

    public List<ReplyResponse> getCurrentUserReplies() {
        return replyRepository.findAllByParentId(authService.getCurrentUser().getId()).stream()
                .map(replyMapper::mapToResponse)
                .collect(Collectors.toList());
    }
}
