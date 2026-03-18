package com.malgn.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ContentCreateRequest(
    @Schema(description = "콘텐츠 제목", example = "첫 번째 콘텐츠")
    @NotBlank(message = "title is required")
    @Size(max = 100, message = "title must be 100 characters or fewer")
    String title,
    @Schema(description = "콘텐츠 본문", example = "콘텐츠 내용입니다.")
    String description
) {
}
