package com.server.community.infrastructure.board;

import com.server.community.application.board.ImageUploader;
import com.server.community.domain.board.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ImageFakeUploader implements ImageUploader {

    @Override
    public void upload(final List<Image> images, final List<MultipartFile> fileImages) {
    }

    @Override
    public void delete(final List<Image> deletedImages) {
    }
}
