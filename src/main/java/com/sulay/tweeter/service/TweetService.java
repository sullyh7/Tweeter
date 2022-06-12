package com.sulay.tweeter.service;

import com.sulay.tweeter.dto.TweetRequest;
import com.sulay.tweeter.dto.TweetResponse;
import com.sulay.tweeter.entity.Tweet;
import com.sulay.tweeter.entity.User;
import com.sulay.tweeter.exception.TweetNotFoundException;
import com.sulay.tweeter.exception.UserNotFoundException;
import com.sulay.tweeter.mapper.TweetMapper;
import com.sulay.tweeter.repostiory.TweetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TweetService {
    private final TweetRepository tweetRepository;
    private final AuthService authService;

    public List<TweetResponse> getTweets() {
        return tweetRepository.findAll().stream()
                .map((tweet) ->TweetMapper.mapToResponse(tweet))
                .collect(Collectors.toList());
    }

    @Transactional
    public TweetResponse tweet(TweetRequest tweetRequest) {
        User currentUser = authService.getCurrentUser();
        return TweetMapper.mapToResponse(tweetRepository.save(TweetMapper.mapToTweet(tweetRequest,
                currentUser)));
    }

    @Transactional(readOnly = true)
    public TweetResponse getTweet(Long id) {
        return TweetMapper.mapToResponse(tweetRepository.findById(id).orElseThrow(
                () -> new TweetNotFoundException("No tweet with id" + id)
        ));
    }

    @Transactional(readOnly = true)
    public List<TweetResponse> getTweetsByKeyword(String keyword) {
        return tweetRepository.findByTitleContains(keyword).stream()
                .map(TweetMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void removeTweet(Long tweetId) {
        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(
                () -> new TweetNotFoundException("No tweet with id " + tweetId)
        );
        User currentUser = authService.getCurrentUser();
        if (tweet.getAuthor().getId().equals(currentUser.getId())) {
            tweetRepository.deleteById(tweetId);
        } else {
            throw new TweetNotFoundException("No tweet with id " + tweetId + " posted by " + currentUser.getUsername());
        }

    }

    @Transactional(readOnly = true)
    public List<TweetResponse> getUserTweets(Long userId) {
        return tweetRepository.findAllByAuthorId(userId).stream()
                .map((tweet) ->TweetMapper.mapToResponse(tweet))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TweetResponse> getCurrentUserTweets() {
        return tweetRepository.findAllByAuthor(authService.getCurrentUser()).stream()
                .map(tweet -> TweetMapper.mapToResponse(tweet))
                .collect(Collectors.toList());
    }
}
