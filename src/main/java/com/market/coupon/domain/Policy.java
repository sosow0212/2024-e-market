package com.market.coupon.domain;

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

    @Column(nullable = false)
    private boolean canUseAlone;

    @Column(nullable = false)
    private boolean isDiscountPercentage;

    @Column(nullable = false)
    private int amount;
}
