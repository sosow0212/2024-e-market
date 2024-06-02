package com.server.market.domain.product.dto;

public record ProductImageResponse(
        Long id,
        String url
) {

    private static final String PREFIX = "https://atwozimage.s3.ap-northeast-2.amazonaws.com/";

    public ProductImageResponse(final Long id, final String url) {
        this.id = id;
        this.url = getUrl(url);
    }

    private static String getUrl(final String url) {
        return PREFIX + url;
    }
}
