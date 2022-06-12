package com.sulay.tweeter.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class TweetRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private String mediaUrl;
}
