package com.malgn.content.dto;

import java.time.LocalDateTime;

import com.malgn.content.domain.Content;

public record ContentSummaryResponse(
    Long id,
    String title,
    long viewCount,
    String createdBy,
    LocalDateTime createdDate,
    LocalDateTime lastModifiedDate
) {

    public static ContentSummaryResponse from(Content content) {
        return new ContentSummaryResponse(
            content.getId(),
            content.getTitle(),
            content.getViewCount(),
            content.getCreatedBy(),
            content.getCreatedDate(),
            content.getLastModifiedDate()
        );
    }
}
