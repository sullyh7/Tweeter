package com.sulay.tweeter.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Data
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String biography;
    private String profileImageUrl;
    private Instant creationDate;
    private int followerCount;
    private boolean isFollowing;
    private boolean isFollowed;
}
