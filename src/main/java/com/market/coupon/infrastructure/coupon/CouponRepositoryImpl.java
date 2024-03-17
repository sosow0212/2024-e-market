package com.market.coupon.infrastructure.coupon;

import com.market.coupon.domain.coupon.Coupon;
import com.market.coupon.domain.coupon.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class CouponRepositoryImpl implements CouponRepository {

    private final CouponJpaRepository couponJpaRepository;

    @Override
    public Coupon save(final Coupon coupon) {
        return couponJpaRepository.save(coupon);
    }

    @Override
    public Optional<Coupon> findById(final Long id) {
        return couponJpaRepository.findById(id);
    }

    @Override
    public void deleteById(final Long id) {
        couponJpaRepository.deleteById(id);
    }

    @Override
    public List<Coupon> findAllByIdsIn(final List<Long> couponIds) {
        return couponJpaRepository.findAllByIdIn(couponIds);
    }

    @Override
    public int countAllByIdIn(final List<Long> couponIds) {
        return couponJpaRepository.countAllByIdIn(couponIds);
    }
}
