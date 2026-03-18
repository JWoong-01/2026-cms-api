package com.malgn.common.exception;

public class ContentNotFoundException extends ResourceNotFoundException {

    public ContentNotFoundException(Long id) {
        super("Content not found. id=" + id);
    }
}
