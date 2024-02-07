package com.market.coupon.ui;

import com.market.coupon.application.CouponService;
import com.market.coupon.application.dto.CouponCreateRequest;
import com.market.coupon.application.dto.CouponDeletedRequest;
import com.market.coupon.application.dto.MemberCouponCreateRequest;
import com.market.coupon.domain.Coupons;
import com.market.coupon.ui.dto.CouponsResponse;
import com.market.member.ui.auth.support.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/api/coupons")
@RestController
public class CouponController {

    private final CouponService couponService;

    @PostMapping
    public ResponseEntity<Long> createCoupon(@RequestBody final CouponCreateRequest request) {
        Long savedCouponId = couponService.saveNewCoupon(request);
        return ResponseEntity.created(URI.create("/api/coupons/" + savedCouponId))
                .build();
    }

    @GetMapping("/members")
    public ResponseEntity<CouponsResponse> findAllMemberCoupons(@AuthMember final Long memberId) {
        Coupons memberCoupons = couponService.findAllMemberCoupons(memberId);
        return ResponseEntity.ok(CouponsResponse.from(memberId, memberCoupons));
    }

    // TODO:
    // Member에게 쿠폰을 주는 것 (API or Event) -> 관리자의 역할
    // Member가 사용한 쿠폰을 제거하는 것 (API or Event)
    @PostMapping("/members/{memberId}")
    public ResponseEntity<Void> saveMemberCoupon(@PathVariable("memberId") final Long memberId,
                                                 @RequestBody final MemberCouponCreateRequest request) {
        couponService.saveMemberCoupons(memberId, request);
        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/members")
    public ResponseEntity<Void> deleteUsingMemberCoupons(@AuthMember final Long memberId,
                                                         @RequestBody final CouponDeletedRequest request) {
        couponService.deleteUsingMemberCoupons(memberId, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
