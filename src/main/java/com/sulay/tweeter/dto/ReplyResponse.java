package com.sulay.tweeter.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.time.Instant;
@Data
@Builder
public class ReplyResponse {
    private Long id;
    private Long parentId;
    private String title;
    private String content;
    private String mediaUrl;
    private BigInteger likes;
    private Instant date;
    private AuthorInfoDto authorInfo;
}
