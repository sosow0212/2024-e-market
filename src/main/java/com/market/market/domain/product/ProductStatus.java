package com.market.market.domain.product;

public enum ProductStatus {

    WAITING,
    RESERVED,
    COMPLETED;

    public boolean isCompleted() {
        return this.equals(COMPLETED);
    }
}
