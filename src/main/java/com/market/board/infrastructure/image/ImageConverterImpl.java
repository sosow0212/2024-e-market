package com.market.board.infrastructure.image;

import com.market.board.domain.image.Image;
import com.market.board.domain.image.ImageConverter;
import com.market.board.exception.exceptions.UnsupportedImageFormatException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ImageConverterImpl implements ImageConverter {

    private static final List<String> supportedExtensions = List.of("jpg", "jpeg", "gif", "bmp", "png");
    private static final String SEPARATOR = ".";

    @Override
    public List<Image> convertImageFilesToImages(final List<MultipartFile> imageFiles) {
        return imageFiles.stream()
                .map(this::convertToImage)
                .toList();
    }

    private Image convertToImage(final MultipartFile imageFile) {
        String originalFilename = imageFile.getOriginalFilename();

        return Image.builder()
                .originName(originalFilename)
                .uniqueName(generateUniqueName(originalFilename))
                .build();
    }

    private String generateUniqueName(final String originName) {
        String extension = getExtension(originName);
        return UUID.randomUUID() + SEPARATOR + extension;
    }

    private String getExtension(final String originName) {
        String extension = originName.substring(originName.lastIndexOf(SEPARATOR) + 1);
        validateSupportedExtension(extension);
        return extension;
    }

    private void validateSupportedExtension(final String extension) {
        boolean supported = supportedExtensions.stream()
                .anyMatch(supportedExtension -> supportedExtension.equalsIgnoreCase(extension));

        if (!supported) {
            throw new UnsupportedImageFormatException();
        }
    }

    @Override
    public List<Image> convertImageIdsToImages(final List<Long> imageIds, final List<Image> images) {
        return imageIds.stream()
                .map(imageId -> convertImageIdToImage(imageId, images))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private Optional<Image> convertImageIdToImage(final Long id, final List<Image> images) {
        return images.stream()
                .filter(image -> image.isSameImageId(id))
                .findAny();
    }
}
