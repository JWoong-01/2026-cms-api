package com.malgn.content.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ContentCreateRequest(
    @NotBlank(message = "title is required")
    @Size(max = 100, message = "title must be 100 characters or fewer")
    String title,
    String description
) {
}
