package com.server.market.infrastructure.product;

import com.server.market.domain.product.ProductImage;
import com.server.market.domain.product.ProductImageConverter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.server.community.fixture.ImageFixture.상품_이미지를_생성한다;

public class ProductImageFakeConverter implements ProductImageConverter {

    @Override
    public List<ProductImage> convertImageFilesToImages(final List<MultipartFile> imageFiles) {
        return List.of(상품_이미지를_생성한다());
    }

    @Override
    public List<ProductImage> convertImageIdsToImages(final List<Long> imageIds, final List<ProductImage> images) {
        return List.of(상품_이미지를_생성한다());
    }
}
