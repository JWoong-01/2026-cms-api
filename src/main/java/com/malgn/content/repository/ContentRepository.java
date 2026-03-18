package com.malgn.content.repository;

import com.malgn.content.domain.Content;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {
}
