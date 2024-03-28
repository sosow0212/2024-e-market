package com.server.coupon.infrastructure.coupon;

import com.server.coupon.domain.coupon.Coupon;
import com.server.coupon.domain.coupon.CouponRepository;
import com.server.coupon.domain.coupon.Description;
import com.server.coupon.domain.coupon.Policy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CouponFakeRepository implements CouponRepository {

    private final Map<Long, Coupon> map = new HashMap<>();

    private Long id = 0L;

    @Override
    public Coupon save(final Coupon coupon) {
        id++;

        Coupon savedCoupon = Coupon.builder()
                .id(id)
                .description(Description.builder()
                        .name(coupon.description.getName())
                        .content(coupon.description.getContent())
                        .build())
                .policy(Policy.builder()
                        .isDiscountPercentage(coupon.getPolicy().isDiscountPercentage())
                        .canUseAlone(coupon.getPolicy().isCanUseAlone())
                        .amount(coupon.policy.getAmount())
                        .build())
                .build();

        map.put(id, coupon);
        return savedCoupon;
    }

    @Override
    public Optional<Coupon> findById(final Long id) {
        return map.keySet().stream()
                .filter(key -> key.equals(id))
                .map(map::get)
                .findAny();
    }

    @Override
    public void deleteById(final Long id) {
        map.remove(id);
    }

    @Override
    public List<Coupon> findAllByIdsIn(final List<Long> couponIds) {
        return map.values().stream()
                .filter(it -> couponIds.contains(it.getId()))
                .toList();
    }

    @Override
    public int countAllByIdIn(final List<Long> couponIds) {
        List<Coupon> coupons = map.values()
                .stream()
                .filter(it -> couponIds.contains(it.getId()))
                .toList();

        return coupons.size();
    }

    @Override
    public boolean isExistedById(final Long couponId) {
        return map.containsKey(couponId);
    }
}
