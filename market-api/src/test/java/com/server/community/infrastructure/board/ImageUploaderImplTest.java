package com.server.community.infrastructure.board;

import com.server.community.domain.board.Image;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.server.community.fixture.ImageFixture.이미지를_생성한다;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ImageUploaderImplTest {

    private final ImageUploaderImpl imageUploader = new ImageUploaderImpl();

    private final MultipartFile file = new MockMultipartFile("name", "origin.jpg", "image", "content".getBytes());

    @Test
    void 이미지를_업로드한다() {
        // given
        List<Image> images = List.of(이미지를_생성한다());
        List<MultipartFile> fileImages = List.of(file);

        // when & then
        assertDoesNotThrow(() -> imageUploader.upload(images, fileImages));
    }

    @Test
    void 이미지를_제거한다() {
        // given
        List<Image> images = List.of(이미지를_생성한다());

        // when & then
        assertDoesNotThrow(() -> imageUploader.delete(images));
    }
}
