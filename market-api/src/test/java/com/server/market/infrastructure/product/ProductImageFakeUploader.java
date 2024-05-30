package com.server.market.infrastructure.product;

import com.server.market.application.product.ProductImageUploader;
import com.server.market.domain.product.ProductImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ProductImageFakeUploader implements ProductImageUploader {

    @Override
    public void upload(final List<ProductImage> images, final List<MultipartFile> fileImages) {
    }

    @Override
    public void delete(final List<ProductImage> deletedImages) {
    }
}
