package com.sulay.tweeter.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReplyRequest {
    private Long parentId;
    private String content;
    private String mediaUrl;
}
