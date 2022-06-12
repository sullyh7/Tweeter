package com.sulay.tweeter.mapper;

import com.sulay.tweeter.dto.AuthorInfoDto;
import com.sulay.tweeter.dto.ReplyRequest;
import com.sulay.tweeter.dto.ReplyResponse;
import com.sulay.tweeter.entity.Reply;
import com.sulay.tweeter.entity.User;
import com.sulay.tweeter.exception.TweeterException;
import com.sulay.tweeter.repostiory.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.Instant;

@RequiredArgsConstructor
@Component
public class ReplyMapper {

    private final TweetRepository tweetService;

    public Reply mapToReply(ReplyRequest replyRequest, User user) {
        return Reply.builder()
                .parent(tweetService.findById(replyRequest.getParentId()).orElseThrow(()
                        -> new TweeterException("No tweet with id " + replyRequest.getParentId())))
                .author(user)
                .date(Instant.now())
                .content(replyRequest.getContent())
                .mediaUrl(replyRequest.getMediaUrl())
                .likes(BigInteger.ZERO)
                .build();
    }

    public ReplyResponse mapToResponse(Reply reply) {
        return ReplyResponse.builder()
                .parentId(reply.getParent().getId())
                .id(reply.getId())
                .content(reply.getContent())
                .mediaUrl(reply.getMediaUrl())
                .authorInfo(AuthorInfoDto.builder()
                        .username(reply.getAuthor().getUsername())
                        .authorId(reply.getAuthor().getId())
                        .profileImageUrl(reply.getAuthor().getProfileImageUrl())
                        .followerCount(reply.getAuthor().getFollowers().size())
                        .build())
                .build();
    }
}
