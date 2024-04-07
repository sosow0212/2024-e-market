package com.server.coupon.infrastructure.coupon;

import com.server.coupon.domain.coupon.MemberCoupon;
import com.server.coupon.domain.coupon.MemberCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class MemberCouponRepositoryImpl implements MemberCouponRepository {

    private final MemberCouponJpaRepository memberCouponJpaRepository;
    private final MemberCouponJdbcRepository memberCouponJdbcRepository;

    @Override
    public List<MemberCoupon> findAllByMemberId(final Long memberId) {
        return memberCouponJpaRepository.findAllByMemberId(memberId);
    }

    @Override
    public MemberCoupon save(final MemberCoupon memberCoupon) {
        return memberCouponJpaRepository.save(memberCoupon);
    }

    @Override
    public void deleteByMemberIdAndCouponIdIn(final Long memberId, final List<Long> couponIds) {
        memberCouponJpaRepository.deleteByMemberIdAndCouponIdIn(memberId, couponIds);
    }

    @Override
    public int countMemberIdWithCouponIds(final Long memberId, final List<Long> couponIds) {
        return memberCouponJpaRepository.countMemberIdWithCouponIds(memberId, couponIds);
    }

    @Override
    public void insertBulk(final List<MemberCoupon> memberCoupons) {
        memberCouponJdbcRepository.insertBulk(memberCoupons);
    }
}
