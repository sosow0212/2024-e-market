package com.server.community.infrastructure.board;

import com.server.community.domain.board.Image;
import com.server.community.domain.board.ImageConverter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.server.community.fixture.ImageFixture.이미지를_생성한다;

public class ImageFakeConverter implements ImageConverter {

    @Override
    public List<Image> convertImageFilesToImages(final List<MultipartFile> imageFiles) {
        return List.of(이미지를_생성한다());
    }

    @Override
    public List<Image> convertImageIdsToImages(final List<Long> imageIds, final List<Image> images) {
        return List.of(이미지를_생성한다());
    }
}
