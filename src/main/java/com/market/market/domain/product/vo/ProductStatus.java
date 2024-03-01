package com.market.market.domain.product.vo;

public enum ProductStatus {

    WAITING,
    RESERVED,
    COMPLETED;

    public boolean isCompleted() {
        return this.equals(COMPLETED);
    }
}
