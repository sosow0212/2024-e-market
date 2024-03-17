package com.market.coupon.application.coupon;

import com.market.coupon.application.coupon.dto.MemberCouponCreateRequest;
import com.market.coupon.domain.voucher.event.UsedVoucherEvent;
import com.market.coupon.domain.voucher.event.ValidatedExistedCouponEvent;
import com.market.market.domain.product.event.CouponExistValidatedEvent;
import com.market.market.domain.product.event.UsedCouponDeletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

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

    @EventListener
    public void validateCouponExisted(final ValidatedExistedCouponEvent event) {
        couponService.validateCouponExisted(event.couponId());
    }

    @EventListener
    public void addMemberCoupon(final UsedVoucherEvent event) {
        couponService.saveMemberCoupons(event.memberId(), new MemberCouponCreateRequest(List.of(event.couponId())));
    }
}
