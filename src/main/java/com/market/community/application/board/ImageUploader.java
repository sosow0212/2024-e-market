package com.market.community.application.board;

import com.market.community.domain.board.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageUploader {

    void upload(final List<Image> images, final List<MultipartFile> fileImages);

    void delete(final List<Image> deletedImages);
}
