package com.server.market.infrastructure.product;

import com.server.community.domain.board.Image;
import com.server.community.domain.board.ImageConverter;
import com.server.community.exception.exceptions.UnsupportedImageFormatException;
import com.server.market.domain.product.ProductImage;
import com.server.market.domain.product.ProductImageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProductImageConverterImpl implements ProductImageConverter {

    private static final List<String> supportedExtensions = List.of("jpg", "jpeg", "gif", "bmp", "png");
    private static final String SEPARATOR = ".";

    @Override
    public List<ProductImage> convertImageFilesToImages(final List<MultipartFile> imageFiles) {
        return imageFiles.stream()
                .map(this::convertToImage)
                .toList();
    }

    private ProductImage convertToImage(final MultipartFile imageFile) {
        String originalFilename = imageFile.getOriginalFilename();

        return ProductImage.builder()
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
    public List<ProductImage> convertImageIdsToImages(final List<Long> imageIds, final List<ProductImage> images) {
        return imageIds.stream()
                .map(imageId -> convertImageIdToImage(imageId, images))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private Optional<ProductImage> convertImageIdToImage(final Long id, final List<ProductImage> images) {
        return images.stream()
                .filter(image -> image.isSameImageId(id))
                .findAny();
    }
}
