package com.malgn.content.dto;

import java.time.LocalDateTime;

import com.malgn.content.domain.Content;

import io.swagger.v3.oas.annotations.media.Schema;

public record ContentDetailResponse(
    @Schema(description = "콘텐츠 ID", example = "1")
    Long id,
    @Schema(description = "콘텐츠 제목", example = "Welcome")
    String title,
    @Schema(description = "콘텐츠 본문", example = "Seeded content by user1")
    String description,
    @Schema(description = "조회수", example = "0")
    long viewCount,
    @Schema(description = "생성자", example = "user1")
    String createdBy,
    @Schema(description = "최종 수정자", example = "user1")
    String lastModifiedBy,
    @Schema(description = "생성일시")
    LocalDateTime createdDate,
    @Schema(description = "최종 수정일시")
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
