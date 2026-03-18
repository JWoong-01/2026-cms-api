package com.malgn.content.dto;

import java.time.LocalDateTime;

import com.malgn.content.domain.Content;

public record ContentDetailResponse(
    Long id,
    String title,
    String description,
    long viewCount,
    String createdBy,
    String lastModifiedBy,
    LocalDateTime createdDate,
    LocalDateTime lastModifiedDate
) {

    public static ContentDetailResponse from(Content content) {
        return new ContentDetailResponse(
            content.getId(),
            content.getTitle(),
            content.getDescription(),
            content.getViewCount(),
            content.getCreatedBy(),
            content.getLastModifiedBy(),
            content.getCreatedDate(),
            content.getLastModifiedDate()
        );
    }
}
