package com.market.coupon.infrastructure;

import com.market.coupon.domain.MemberCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberCouponJpaRepository extends JpaRepository<MemberCoupon, Long> {

    List<MemberCoupon> findAllByMemberId(Long memberId);

    MemberCoupon save(MemberCoupon memberCoupon);

    void deleteByMemberIdAndCouponIdIn(Long memberId, List<Long> couponIds);
}
