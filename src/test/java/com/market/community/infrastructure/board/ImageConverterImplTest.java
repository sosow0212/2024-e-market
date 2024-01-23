package com.market.community.infrastructure.board;

import com.market.community.domain.board.Image;
import com.market.community.exception.exceptions.UnsupportedImageFormatException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.market.community.fixture.ImageFixture.이미지를_생성한다;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class ImageConverterImplTest {

    private static final List<String> supportedExtensions = List.of("jpg", "jpeg", "gif", "bmp", "png");
    private static final Image 이미지를_생성한다 = 이미지를_생성한다();

    private final ImageConverterImpl imageConverter = new ImageConverterImpl();

    @DisplayName("이미지_파일을_이미지로_변환한다")
    @Nested
    class ConvertImageFileToImage {

        @Test
        void 이미지파일을_이미지로_변환한다() {
            // given
            MultipartFile mockImage = new MockMultipartFile("image", "image.jpg", "image", "content".getBytes());
            List<MultipartFile> imageFiles = new ArrayList<>(List.of(mockImage));

            // when
            List<Image> result = imageConverter.convertImageFilesToImages(imageFiles);

            // then
            assertSoftly(softly -> {
                softly.assertThat(result).hasSize(1);
            });
        }

        @ValueSource(strings = {"jpg", "jpeg", "gif", "bmp", "png"})
        @ParameterizedTest
        void 확장자에_맞다면_예외가_발생하지_않는다(final String extension) {
            // given
            MultipartFile mockImage = new MockMultipartFile("image", "image." + extension, "image", "content".getBytes());
            List<MultipartFile> imageFiles = new ArrayList<>(List.of(mockImage));

            // when & then
            assertDoesNotThrow(() -> imageConverter.convertImageFilesToImages(imageFiles));
        }

        @ValueSource(strings = {"a", "mp3"})
        @ParameterizedTest
        void 확장자가_다르다면_예외를_발생시킨다(final String extension) {
            // given
            MultipartFile mockImage = new MockMultipartFile("image", "image." + extension, "image", "content".getBytes());
            List<MultipartFile> imageFiles = new ArrayList<>(List.of(mockImage));

            // when & then
            assertThatThrownBy(() -> imageConverter.convertImageFilesToImages(imageFiles))
                    .isInstanceOf(UnsupportedImageFormatException.class);
        }
    }

    @Nested
    class ConvertImageIdsToImages {

        @Test
        void 이미지_id를_이미지로_변환한다() {
            // given
            Image image = 이미지를_생성한다;
            List<Long> imageIds = List.of(image.getId());
            List<Image> images = List.of(image);

            // when
            List<Image> result = imageConverter.convertImageIdsToImages(imageIds, images);

            // then
            assertSoftly(softly -> {
                softly.assertThat(result).hasSize(1);
                softly.assertThat(result.get(0)).usingRecursiveComparison().isEqualTo(image);
            });
        }
    }
}
