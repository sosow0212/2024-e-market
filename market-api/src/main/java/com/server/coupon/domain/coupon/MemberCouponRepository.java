package com.server.coupon.domain.coupon;

import java.util.List;

public interface MemberCouponRepository {

    List<MemberCoupon> findAllByMemberId(Long memberId);

    MemberCoupon save(MemberCoupon memberCoupon);

    void deleteByMemberIdAndCouponIdIn(Long memberId, List<Long> couponIds);

    int countMemberIdWithCouponIds(Long memberId, List<Long> couponIds);

    void insertBulk(List<MemberCoupon> memberCoupons);
}
