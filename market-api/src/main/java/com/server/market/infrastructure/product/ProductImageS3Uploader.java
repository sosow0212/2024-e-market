package com.server.market.infrastructure.product;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.server.community.exception.exceptions.FileUploadFailureException;
import com.server.market.application.product.ProductImageUploader;
import com.server.market.domain.product.ProductImage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor
@Profile("prod")
@Component
public class ProductImageS3Uploader implements ProductImageUploader {

    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public void upload(final List<ProductImage> images, final List<MultipartFile> fileImages) {
        IntStream.range(0, images.size())
                .forEach(index -> saveFile(
                        fileImages.get(index),
                        images.get(index).getUniqueName()
                ));
    }

    private void saveFile(final MultipartFile file, final String fileUniqueName) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try {
            amazonS3Client.putObject(bucket, fileUniqueName, file.getInputStream(), metadata);
            log.info("productImage 파일 저장 완료");
        } catch (IOException e) {
            log.info("파일 저장 실패 : " + bucket + " " + fileUniqueName);
            throw new FileUploadFailureException(e);
        }
    }

    @Override
    public void delete(final List<ProductImage> deletedImages) {
        deletedImages.forEach(image -> deleteImage(image.getUniqueName()));
    }

    private void deleteImage(final String fileUrl) {
        String[] urlParts = fileUrl.split("/");
        String objectKey = String.join("/", Arrays.copyOfRange(urlParts, 3, urlParts.length));

        try {
            amazonS3Client.deleteObject(bucket, objectKey);
        } catch (AmazonS3Exception e) {
            log.error("File delete fail : " + e.getMessage());
            throw new FileUploadFailureException(e.getCause());
        } catch (SdkClientException e) {
            log.error("AWS SDK client error : " + e.getMessage());
            throw new FileUploadFailureException(e.getCause());
        }

        log.info("File delete complete: " + objectKey);
    }
}
