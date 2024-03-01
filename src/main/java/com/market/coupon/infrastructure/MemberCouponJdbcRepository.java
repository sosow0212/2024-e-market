package com.market.coupon.infrastructure;

import com.market.coupon.domain.MemberCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class MemberCouponJdbcRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void insertBulk(final List<MemberCoupon> memberCoupons) {
        String sql = "INSERT IGNORE INTO member_coupon (member_id, coupon_id, created_at, updated_at)" +
                " VALUES (:memberId, :couponId, :createdAt, :updatedAt)";

        namedParameterJdbcTemplate.batchUpdate(sql, chargeStationSqlParameterSource(memberCoupons));
    }

    private MapSqlParameterSource[] chargeStationSqlParameterSource(Collection<MemberCoupon> memberCoupons) {
        return memberCoupons.stream()
                .map(this::changeToSqlParameterSource)
                .toArray(MapSqlParameterSource[]::new);
    }

    private MapSqlParameterSource changeToSqlParameterSource(final MemberCoupon memberCoupon) {
        LocalDateTime now = LocalDateTime.now();

        return new MapSqlParameterSource()
                .addValue("memberId", memberCoupon.getMemberId())
                .addValue("couponId", memberCoupon.getCouponId())
                .addValue("createdAt", now)
                .addValue("updatedAt", now);
    }
}
