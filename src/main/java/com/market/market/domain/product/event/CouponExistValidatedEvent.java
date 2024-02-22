package com.market.market.domain.product.event;

import com.market.global.event.Event;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CouponExistValidatedEvent extends Event {

    private final Long memberId;
    private final List<Long> usingCouponIds;
}
