package com.market.coupon.infrastructure;

import com.market.coupon.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CouponJpaRepository extends JpaRepository<Coupon, Long> {

    Coupon save(Coupon coupon);

    Optional<Coupon> findById(Long id);

    void deleteById(Long id);

    List<Coupon> findAllByIdIn(List<Long> ids);
}
