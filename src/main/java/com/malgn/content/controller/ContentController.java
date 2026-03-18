package com.malgn.content.controller;

import com.malgn.common.response.PageResponse;
import com.malgn.content.dto.ContentCreateRequest;
import com.malgn.content.dto.ContentDetailResponse;
import com.malgn.content.dto.ContentSummaryResponse;
import com.malgn.content.dto.ContentUpdateRequest;
import com.malgn.content.service.ContentService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/contents")
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService;

    @PostMapping
    public ResponseEntity<ContentDetailResponse> create(@Valid @RequestBody ContentCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contentService.create(request));
    }

    @GetMapping
    public ResponseEntity<PageResponse<ContentSummaryResponse>> getContents(
        @RequestParam(defaultValue = "0") @Min(0) int page,
        @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size
    ) {
        return ResponseEntity.ok(contentService.getContents(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContentDetailResponse> getContent(@PathVariable Long id) {
        return ResponseEntity.ok(contentService.getContent(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContentDetailResponse> update(
        @PathVariable Long id,
        @Valid @RequestBody ContentUpdateRequest request
    ) {
        return ResponseEntity.ok(contentService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        contentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
