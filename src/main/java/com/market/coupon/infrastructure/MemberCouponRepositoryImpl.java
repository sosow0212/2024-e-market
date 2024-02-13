package com.market.coupon.infrastructure;

import com.market.coupon.domain.MemberCoupon;
import com.market.coupon.domain.MemberCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class MemberCouponRepositoryImpl implements MemberCouponRepository {

    private final MemberCouponJpaRepository memberCouponJpaRepository;

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
}
