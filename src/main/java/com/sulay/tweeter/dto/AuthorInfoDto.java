package com.sulay.tweeter.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorInfoDto {
    private String username;
    private String profileImageUrl;
    private Long authorId;
    private int followerCount;
}
