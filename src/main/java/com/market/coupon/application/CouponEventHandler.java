package com.market.coupon.application;

import com.market.market.domain.product.event.CouponExistValidatedEvent;
import com.market.market.domain.product.event.UsedCouponDeletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CouponEventHandler {

    private final CouponService couponService;

    @EventListener
    public void validateMemberCouponExisted(final CouponExistValidatedEvent event) {
        couponService.validateMemberCouponsExisted(event.getMemberId(), event.getUsingCouponIds());
    }

    @EventListener
    public void deleteUsedMemberCoupons(final UsedCouponDeletedEvent event) {
        couponService.deleteUsedMemberCoupons(event.getBuyerId(), event.getUsedCoupons());
    }
}
