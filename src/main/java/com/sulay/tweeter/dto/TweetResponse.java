package com.sulay.tweeter.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigInteger;
import java.time.Instant;

@Data
@Builder
public class TweetResponse {
    private Long id;
    private String title;
    private String content;
    private String mediaUrl;
    private BigInteger likes;
    private Instant date;
    private AuthorInfoDto authorInfo;
}
