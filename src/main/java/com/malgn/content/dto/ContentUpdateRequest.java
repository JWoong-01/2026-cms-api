package com.malgn.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ContentUpdateRequest(
    @Schema(description = "수정할 제목", example = "수정된 제목")
    @NotBlank(message = "title is required")
    @Size(max = 100, message = "title must be 100 characters or fewer")
    String title,
    @Schema(description = "수정할 본문", example = "수정된 내용입니다.")
    String description
) {
}
