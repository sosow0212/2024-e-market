package com.server.coupon.infrastructure.voucher;

import com.server.coupon.domain.voucher.dto.VoucherSimpleResponse;
import com.server.coupon.domain.voucher.dto.VoucherSpecificResponse;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.server.coupon.domain.coupon.QCoupon.coupon;
import static com.server.coupon.domain.voucher.QVoucher.voucher;
import static com.querydsl.core.types.Projections.constructor;

@RequiredArgsConstructor
@Repository
public class VoucherQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<VoucherSimpleResponse> findAllVouchers(final Pageable pageable) {
        QueryResults<VoucherSimpleResponse> result = jpaQueryFactory.select(
                        constructor(VoucherSimpleResponse.class,
                                voucher.id,
                                voucher.couponId,
                                voucher.description,
                                voucher.isPublic,
                                voucher.isUsed,
                                voucher.createdAt
                        )).from(voucher)
                .orderBy(voucher.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    public Optional<VoucherSpecificResponse> findById(final Long voucherId) {
        return Optional.ofNullable(
                jpaQueryFactory.select(
                                constructor(VoucherSpecificResponse.class,
                                        voucher.id,
                                        voucher.couponId,
                                        coupon.description.name,
                                        coupon.policy.canUseAlone,
                                        coupon.policy.isDiscountPercentage,
                                        coupon.policy.amount,
                                        voucher.description,
                                        voucher.voucherNumber,
                                        voucher.isPublic,
                                        voucher.isUsed,
                                        voucher.createdAt
                                )
                        ).from(voucher)
                        .where(voucher.id.eq(voucherId))
                        .leftJoin(coupon).on(voucher.couponId.eq(coupon.id))
                        .fetchOne()
        );
    }
}
