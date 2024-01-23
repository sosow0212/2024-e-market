package com.market.community.infrastructure.board;

import com.market.community.domain.board.Image;
import com.market.community.domain.board.ImageConverter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.market.community.fixture.ImageFixture.이미지를_생성한다;

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
