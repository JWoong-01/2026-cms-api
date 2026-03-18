package com.malgn.content.service;

import com.malgn.common.exception.ContentNotFoundException;
import com.malgn.common.security.CurrentUserAccessor;
import com.malgn.common.security.SecurityUser;
import com.malgn.common.response.PageResponse;
import com.malgn.content.domain.Content;
import com.malgn.content.dto.ContentCreateRequest;
import com.malgn.content.dto.ContentDetailResponse;
import com.malgn.content.dto.ContentSummaryResponse;
import com.malgn.content.dto.ContentUpdateRequest;
import com.malgn.content.repository.ContentRepository;
import com.malgn.user.domain.UserRole;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentService {

    private final ContentRepository contentRepository;

    @Transactional
    public ContentDetailResponse create(ContentCreateRequest request) {
        SecurityUser currentUser = CurrentUserAccessor.getCurrentUser();
        Content content = Content.create(request.title(), request.description(), currentUser.username());
        return ContentDetailResponse.from(contentRepository.save(content));
    }

    public PageResponse<ContentSummaryResponse> getContents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        return PageResponse.from(
            contentRepository.findAll(pageable).map(ContentSummaryResponse::from)
        );
    }

    public ContentDetailResponse getContent(Long id) {
        return ContentDetailResponse.from(findContent(id));
    }

    @Transactional
    public ContentDetailResponse update(Long id, ContentUpdateRequest request) {
        SecurityUser currentUser = CurrentUserAccessor.getCurrentUser();
        Content content = findContent(id);
        validateEditable(content, currentUser);

        content.update(request.title(), request.description(), currentUser.username());
        return ContentDetailResponse.from(content);
    }

    @Transactional
    public void delete(Long id) {
        SecurityUser currentUser = CurrentUserAccessor.getCurrentUser();
        Content content = findContent(id);
        validateEditable(content, currentUser);
        contentRepository.delete(content);
    }

    private Content findContent(Long id) {
        return contentRepository.findById(id)
            .orElseThrow(() -> new ContentNotFoundException(id));
    }

    private void validateEditable(Content content, SecurityUser currentUser) {
        if (currentUser.role() == UserRole.ADMIN) {
            return;
        }

        if (!content.getCreatedBy().equals(currentUser.username())) {
            throw new AccessDeniedException("Only the author or ADMIN can modify/delete this content");
        }
    }
}
