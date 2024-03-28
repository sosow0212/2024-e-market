package com.server.market.exception.exceptions;

public class ProductOwnerNotEqualsException extends RuntimeException {

    public ProductOwnerNotEqualsException() {
        super("요청하신 유저와 상품의 주인이 일치하지 않습니다.");
    }
}
