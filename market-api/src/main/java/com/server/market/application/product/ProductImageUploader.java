package com.server.market.application.product;

import com.server.community.domain.board.Image;
import com.server.market.domain.product.ProductImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductImageUploader {

    void upload(final List<ProductImage> images, final List<MultipartFile> fileImages);

    void delete(final List<ProductImage> deletedImages);
}
