package com.server.market.application.product.dto;

import com.server.market.domain.product.ProductImage;

import java.util.ArrayList;
import java.util.List;

public record ProductUpdateResult(
        List<ProductImage> addedImages,
        List<ProductImage> deletedImages
) {

    public ProductUpdateResult(final List<ProductImage> addedImages, final List<ProductImage> deletedImages) {
        this.addedImages = handleAddedImage(addedImages);
        this.deletedImages = handleDeletedImages(deletedImages);
    }

    private static List<ProductImage> handleAddedImage(final List<ProductImage> addedImages) {
        if (addedImages == null) {
            return new ArrayList<>();
        }

        return addedImages;
    }

    private List<ProductImage> handleDeletedImages(final List<ProductImage> deletedImages) {
        if (deletedImages == null) {
            return new ArrayList<>();
        }

        return deletedImages;
    }
}
