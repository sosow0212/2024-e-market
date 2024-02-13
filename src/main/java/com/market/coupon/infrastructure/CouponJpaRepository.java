package com.market.coupon.infrastructure;

import com.market.coupon.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponJpaRepository extends JpaRepository<Coupon, Long> {

    Coupon save(final Coupon coupon);

    Optional<Coupon> findById(final Long id);

    void deleteById(final Long id);
}
