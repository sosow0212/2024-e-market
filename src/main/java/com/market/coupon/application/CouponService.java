package com.market.coupon.application;

import com.market.coupon.application.dto.CouponCreateRequest;
import com.market.coupon.application.dto.MemberCouponCreateRequest;
import com.market.coupon.domain.ApplyPolicy;
import com.market.coupon.domain.Coupon;
import com.market.coupon.domain.CouponRepository;
import com.market.coupon.domain.Coupons;
import com.market.coupon.domain.MemberCoupon;
import com.market.coupon.domain.MemberCouponRepository;
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
        List<Long> couponIds = request.couponIds();
        validateExistedInCouponsIds(couponIds);

        List<MemberCoupon> memberCoupons = couponIds.stream()
                .map(it -> MemberCoupon.builder()
                        .memberId(memberId)
                        .couponId(it)
                        .build()
                ).toList();

        memberCouponRepository.insertBulk(memberCoupons);
    }

    private void validateExistedInCouponsIds(final List<Long> couponIds) {
        if (couponRepository.countAllByIdIn(couponIds) != couponIds.size()) {
            throw new CouponNotFoundException();
        }
    }

    @Transactional(readOnly = true)
    public Coupons findAllMemberCoupons(final Long memberId, final Long authId) {
        validateAuthentication(memberId, authId);

        List<Long> couponIds = memberCouponRepository.findAllByMemberId(memberId).stream()
                .map(MemberCoupon::getCouponId)
                .toList();

        List<Coupon> coupons = couponRepository.findAllByIdsIn(couponIds);

        return new Coupons(coupons);
    }

    private void validateAuthentication(final Long memberId, final Long authId) {
        if (!memberId.equals(authId)) {
            throw new AuthenticationInvalidException();
        }
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
        Coupons coupons = new Coupons(foundCoupons);
        coupons.validateContainsNotExistedCoupon(couponIds);
        return coupons.applyCoupons(productPrice, applyPolicy);
    }
}
