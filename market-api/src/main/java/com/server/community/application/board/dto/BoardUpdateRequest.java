package com.server.community.application.board.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public record BoardUpdateRequest(
        @NotBlank(message = "게시글 제목을 입력해주세요.")
        String title,

        @NotBlank(message = "게시글 내용을 작성해주세요.")
        String content,

        List<MultipartFile> addedImages,

        List<Long> deletedImages
) {

    public BoardUpdateRequest(final String title,
                              final String content,
                              final List<MultipartFile> addedImages,
                              final List<Long> deletedImages
    ) {
        this.title = title;
        this.content = content;
        this.addedImages = handleAddedImages(addedImages);
        this.deletedImages = handleDeletedImages(deletedImages);
    }

    private List<MultipartFile> handleAddedImages(final List<MultipartFile> addedImages) {
        if (addedImages == null) {
            return new ArrayList<>();
        }

        return addedImages;
    }

    private List<Long> handleDeletedImages(final List<Long> deletedImages) {
        if (deletedImages == null) {
            return new ArrayList<>();
        }

        return deletedImages;
    }
}
