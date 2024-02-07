package com.market.coupon.application;

import com.market.coupon.application.dto.CouponCreateRequest;
import com.market.coupon.application.dto.CouponDeletedRequest;
import com.market.coupon.application.dto.MemberCouponCreateRequest;
import com.market.coupon.domain.Coupon;
import com.market.coupon.domain.CouponRepository;
import com.market.coupon.domain.Coupons;
import com.market.coupon.domain.MemberCoupon;
import com.market.coupon.domain.MemberCouponRepository;
import com.market.coupon.exception.exceptions.CouponNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;

    @Transactional
    public Long saveNewCoupon(final CouponCreateRequest request) {
        Coupon coupon = Coupon.createCoupon(request.name(), request.content(), request.canUseAlone(), request.isDiscountPercentage(), request.amount());
        return couponRepository.save(coupon)
                .getId();
    }

    @Transactional
    public void saveMemberCoupons(final Long memberId, final MemberCouponCreateRequest request) {
        request.couponIds().forEach(couponId -> {
            findCoupon(couponId);
            MemberCoupon memberCoupon = MemberCoupon.builder()
                    .memberId(memberId)
                    .couponId(couponId)
                    .build();

            memberCouponRepository.save(memberCoupon);
        });
    }

    // TODO: 추후 한 번에 조회하도록 변경 필요
    @Transactional(readOnly = true)
    public Coupons findAllMemberCoupons(final Long memberId) {
        List<Coupon> foundMemberCoupons = memberCouponRepository.findAllByMemberId(memberId).stream()
                .map(memberCoupon -> findCoupon(memberCoupon.getCouponId()))
                .toList();

        return new Coupons(foundMemberCoupons);
    }

    private Coupon findCoupon(final Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(CouponNotFoundException::new);
    }

    @Transactional
    public void deleteUsingMemberCoupons(final Long memberId, final CouponDeletedRequest request) {
        List<Long> deletedCouponsIds = request.deletedCouponIds();
        memberCouponRepository.deleteByMemberIdAndCouponIdIn(memberId, deletedCouponsIds);
    }
}
