package com.market.market.exception.exceptions;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException() {
        super("상품을 찾을 수 없습니다.");
    }
}
