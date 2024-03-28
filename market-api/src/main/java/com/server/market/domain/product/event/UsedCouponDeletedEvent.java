package com.server.market.domain.product.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class UsedCouponDeletedEvent {

    private final Long buyerId;
    private final List<Long> usedCoupons;
}
