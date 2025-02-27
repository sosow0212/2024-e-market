package com.server.coupon.domain.coupon;

import java.util.List;
import java.util.Optional;

public interface CouponRepository {

    Coupon save(Coupon coupon);

    Optional<Coupon> findById(Long id);

    void deleteById(Long id);

    List<Coupon> findAllByIdsIn(List<Long> couponIds);

    int countAllByIdIn(List<Long> couponIds);

    boolean isExistedById(Long couponId);
}
