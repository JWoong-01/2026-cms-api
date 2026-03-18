package com.malgn.content.domain;

import com.malgn.common.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "contents")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Content extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    private String description;

    @Column(name = "view_count", nullable = false)
    private long viewCount;

    @Column(name = "created_by", nullable = false, updatable = false, length = 50)
    private String createdBy;

    @Column(name = "last_modified_by", length = 50)
    private String lastModifiedBy;

    @Builder
    private Content(String title, String description, long viewCount, String createdBy, String lastModifiedBy) {
        this.title = title;
        this.description = description;
        this.viewCount = viewCount;
        this.createdBy = createdBy;
        this.lastModifiedBy = lastModifiedBy;
    }

    public static Content create(String title, String description, String createdBy) {
        return Content.builder()
            .title(title)
            .description(description)
            .viewCount(0L)
            .createdBy(createdBy)
            .lastModifiedBy(createdBy)
            .build();
    }

    public static Content seed(String title, String description, String createdBy) {
        return create(title, description, createdBy);
    }

    public void update(String title, String description, String modifiedBy) {
        this.title = title;
        this.description = description;
        this.lastModifiedBy = modifiedBy;
    }
}
