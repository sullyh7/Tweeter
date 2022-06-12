package com.sulay.tweeter.mapper;

import com.sulay.tweeter.dto.AuthorInfoDto;
import com.sulay.tweeter.dto.TweetRequest;
import com.sulay.tweeter.dto.TweetResponse;
import com.sulay.tweeter.entity.Tweet;
import com.sulay.tweeter.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.Instant;

@RequiredArgsConstructor
@Service
public class TweetMapper {
    public static Tweet mapToTweet(TweetRequest tweetRequest, User user) {
        return Tweet.builder()
                .author(user)
                .date(Instant.now())
                .title(tweetRequest.getTitle())
                .content(tweetRequest.getContent())
                .mediaURl(tweetRequest.getMediaUrl())
                .likes(BigInteger.ZERO)
                .build();
    }

    public static TweetResponse mapToResponse(Tweet tweet) {
        User author = tweet.getAuthor();
        return TweetResponse.builder()
                .id(tweet.getId())
                .likes(tweet.getLikes())
                .date(tweet.getDate())
                .content(tweet.getContent())
                .title(tweet.getTitle())
                .authorInfo(AuthorInfoDto.builder()
                        .username(author.getUsername())
                        .profileImageUrl(author.getProfileImageUrl())
                        .authorId(author.getId())
                        .followerCount(author.getFollowers().size())
                        .build())
                .build();
    }
}
