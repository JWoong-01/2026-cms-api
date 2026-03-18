package com.malgn.content.dto;

import java.time.LocalDateTime;

import com.malgn.content.domain.Content;

import io.swagger.v3.oas.annotations.media.Schema;

public record ContentSummaryResponse(
    @Schema(description = "콘텐츠 ID", example = "1")
    Long id,
    @Schema(description = "콘텐츠 제목", example = "Welcome")
    String title,
    @Schema(description = "조회수", example = "0")
    long viewCount,
    @Schema(description = "생성자", example = "user1")
    String createdBy,
    @Schema(description = "생성일시")
    LocalDateTime createdDate,
    @Schema(description = "최종 수정일시")
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
