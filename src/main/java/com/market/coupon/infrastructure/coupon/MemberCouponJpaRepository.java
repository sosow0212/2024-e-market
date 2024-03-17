package com.market.coupon.infrastructure.coupon;

import com.market.coupon.domain.coupon.MemberCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberCouponJpaRepository extends JpaRepository<MemberCoupon, Long> {

    List<MemberCoupon> findAllByMemberId(Long memberId);

    MemberCoupon save(MemberCoupon memberCoupon);

    void deleteByMemberIdAndCouponIdIn(Long memberId, List<Long> couponIds);

    @Query("SELECT COUNT(mc) FROM MemberCoupon mc WHERE mc.memberId = :memberId AND mc.couponId IN :couponIds")
    int countMemberIdWithCouponIds(@Param("memberId") Long memberId, @Param("couponIds") List<Long> couponIds);
}
