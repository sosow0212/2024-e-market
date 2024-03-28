package com.server.market.domain.product.vo;

public enum ProductStatus {

    WAITING,
    RESERVED,
    COMPLETED;

    public boolean isCompleted() {
        return this.equals(COMPLETED);
    }
}
