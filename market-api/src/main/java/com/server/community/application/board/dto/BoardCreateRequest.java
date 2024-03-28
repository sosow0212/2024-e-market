package com.server.community.application.board.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public record BoardCreateRequest(
        @NotBlank(message = "게시글 제목을 입력해주세요.")
        String title,

        @NotBlank(message = "게시글 내용을 작성해주세요.")
        String content,

        List<MultipartFile> images
) {

    public BoardCreateRequest(final String title, final String content, final List<MultipartFile> images) {
        this.title = title;
        this.content = content;
        this.images = handleAddedImages(images);
    }

    private List<MultipartFile> handleAddedImages(final List<MultipartFile> addedImages) {
        if (addedImages == null) {
            return new ArrayList<>();
        }

        return addedImages;
    }
}
