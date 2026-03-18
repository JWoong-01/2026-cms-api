package com.malgn.common.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.data.domain.Page;

public record PageResponse<T>(
    @Schema(description = "현재 페이지의 데이터 목록")
    List<T> items,
    @Schema(description = "현재 페이지 번호", example = "0")
    int page,
    @Schema(description = "페이지 크기", example = "10")
    int size,
    @Schema(description = "전체 데이터 수", example = "3")
    long totalElements,
    @Schema(description = "전체 페이지 수", example = "1")
    int totalPages
) {

    public static <T> PageResponse<T> from(Page<T> page) {
        return new PageResponse<>(
            page.getContent(),
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages()
        );
    }
}
