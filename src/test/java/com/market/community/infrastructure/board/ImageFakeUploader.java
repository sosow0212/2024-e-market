package com.market.community.infrastructure.board;

import com.market.community.application.board.ImageUploader;
import com.market.community.domain.board.Image;
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
