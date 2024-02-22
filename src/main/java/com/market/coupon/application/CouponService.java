package com.market.coupon.application;

import com.market.coupon.application.dto.CouponCreateRequest;
import com.market.coupon.application.dto.MemberCouponCreateRequest;
import com.market.coupon.domain.ApplyPolicy;
import com.market.coupon.domain.Coupon;
import com.market.coupon.domain.CouponRepository;
import com.market.coupon.domain.Coupons;
import com.market.coupon.domain.MemberCoupon;
import com.market.coupon.domain.MemberCouponRepository;
import com.market.coupon.exception.exceptions.ContainsNotExistedCouponException;
import com.market.coupon.exception.exceptions.CouponNotFoundException;
import com.market.coupon.exception.exceptions.MemberCouponSizeNotEqualsException;
import com.market.global.exception.exception.AuthenticationInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final ApplyPolicy applyPolicy;

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
    public Coupons findAllMemberCoupons(final Long memberId, final Long authId) {
        validateAuthentication(memberId, authId);

        List<Coupon> foundMemberCoupons = memberCouponRepository.findAllByMemberId(memberId).stream()
                .map(memberCoupon -> findCoupon(memberCoupon.getCouponId()))
                .toList();

        return new Coupons(foundMemberCoupons);
    }

    private void validateAuthentication(final Long memberId, final Long authId) {
        if (!memberId.equals(authId)) {
            throw new AuthenticationInvalidException();
        }
    }

    private Coupon findCoupon(final Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(CouponNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public void validateMemberCouponsExisted(final Long memberId, final List<Long> usingCouponIds) {
        int count = memberCouponRepository.countMemberIdWithCouponIds(memberId, usingCouponIds);

        if (usingCouponIds.size() != count) {
            throw new MemberCouponSizeNotEqualsException();
        }
    }

    @Transactional
    public void deleteUsedMemberCoupons(final Long buyerId, final List<Long> usedCoupons) {
        memberCouponRepository.deleteByMemberIdAndCouponIdIn(buyerId, usedCoupons);
    }

    @Transactional(readOnly = true)
    public int applyCoupons(final Integer productPrice, final List<Long> couponIds) {
        List<Coupon> foundCoupons = couponRepository.findAllByIdsIn(couponIds);
        validateContainsNotExistedCoupon(foundCoupons, couponIds);

        Coupons coupons = new Coupons(foundCoupons);
        return coupons.applyCoupons(productPrice, applyPolicy);
    }

    private void validateContainsNotExistedCoupon(final List<Coupon> foundCoupons, final List<Long> couponIds) {
        if (foundCoupons.size() != couponIds.size()) {
            throw new ContainsNotExistedCouponException();
        }
    }
}
