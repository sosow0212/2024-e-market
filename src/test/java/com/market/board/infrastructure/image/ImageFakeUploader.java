package com.market.board.infrastructure.image;

import com.market.board.domain.image.Image;
import com.market.board.domain.image.ImageUploader;
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
