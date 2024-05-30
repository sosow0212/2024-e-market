package com.server.coupon.ui.coupon;

import com.server.coupon.application.coupon.CouponService;
import com.server.coupon.application.coupon.dto.CouponCreateRequest;
import com.server.coupon.application.coupon.dto.MemberCouponCreateRequest;
import com.server.coupon.domain.coupon.Coupons;
import com.server.coupon.ui.coupon.dto.ApplyCouponResponse;
import com.server.coupon.ui.coupon.dto.CouponsResponse;
import com.server.member.ui.auth.support.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/coupons")
    public ResponseEntity<Long> createCoupon(@RequestBody final CouponCreateRequest request) {
        Long savedCouponId = couponService.saveNewCoupon(request);
        return ResponseEntity.created(URI.create("/api/coupons/" + savedCouponId))
                .build();
    }

    @GetMapping("/members/{memberId}/coupons")
    public ResponseEntity<CouponsResponse> findAllMemberCoupons(
            @PathVariable("memberId") final Long memberId,
            @AuthMember final Long authId
    ) {
        Coupons memberCoupons = couponService.findAllMemberCoupons(memberId, authId);
        return ResponseEntity.ok(CouponsResponse.from(memberId, memberCoupons));
    }

    @PostMapping("/members/{memberId}/coupons")
    public ResponseEntity<Void> saveMemberCoupon(
            @PathVariable("memberId") final Long memberId,
            @RequestBody final MemberCouponCreateRequest request
    ) {
        couponService.saveMemberCoupons(memberId, request);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/coupons")
    public ResponseEntity<ApplyCouponResponse> applyCoupons(
            @RequestParam(value = "couponIds") final List<Long> couponIds,
            @RequestParam(value = "price") final Integer price
    ) {
        int discountPrice = couponService.applyCoupons(price, couponIds);
        return ResponseEntity.ok(new ApplyCouponResponse(price, discountPrice, couponIds));
    }
}
