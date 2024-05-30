package com.server.market.domain.product;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductImageConverter {

    List<ProductImage> convertImageFilesToImages(final List<MultipartFile> imageFiles);

    List<ProductImage> convertImageIdsToImages(final List<Long> imageIds, final List<ProductImage> images);
}
