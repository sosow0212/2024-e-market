package com.server.coupon.domain.coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Policy {

    private static final int FREE_PRICE = 0;
    private static final double PERCENTAGE_VALUE = 0.01;

    @Column(nullable = false)
    private boolean canUseAlone;

    @Column(nullable = false)
    private boolean isDiscountPercentage;

    @Column(nullable = false)
    private int amount;

    public int discount(final Integer price) {
        if (this.isDiscountPercentage) {
            return getDiscountPercentageAmountFrom(price);
        }

        return getDiscountAmountFrom(price);

    }

    private int getDiscountPercentageAmountFrom(final Integer price) {
        double percentage = 1 - (amount * PERCENTAGE_VALUE);
        return (int) (price * percentage);
    }

    private int getDiscountAmountFrom(final Integer price) {
        if (this.amount > price) {
            return FREE_PRICE;
        }

        return price - this.amount;
    }
}
