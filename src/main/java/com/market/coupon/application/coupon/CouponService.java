package com.market.coupon.application.coupon;

import com.market.coupon.application.coupon.dto.CouponCreateRequest;
import com.market.coupon.application.coupon.dto.MemberCouponCreateRequest;
import com.market.coupon.domain.coupon.ApplyPolicy;
import com.market.coupon.domain.coupon.Coupon;
import com.market.coupon.domain.coupon.CouponRepository;
import com.market.coupon.domain.coupon.Coupons;
import com.market.coupon.domain.coupon.MemberCoupon;
import com.market.coupon.domain.coupon.MemberCouponRepository;
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

    @Transactional(readOnly = true)
    public void validateCouponExisted(final Long couponId) {
        if (!couponRepository.isExistedById(couponId)) {
            throw new CouponNotFoundException();
        }
    }
}
