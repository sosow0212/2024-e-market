package com.server.coupon.domain.coupon;

import com.server.coupon.exception.exceptions.CouponAmountRangeInvalidException;
import com.server.global.domain.BaseEntity;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@EqualsAndHashCode(of = "id", callSuper = false)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Coupon extends BaseEntity {

    private static final int MINIMUM_PERCENTAGE_DISCOUNT_AMOUNT = 0;
    private static final int MAXIMUM_PERCENTAGE_DISCOUNT_AMOUNT = 100;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    public Description description;

    @Embedded
    public Policy policy;

    public static Coupon createCoupon(final String name,
                                      final String content,
                                      final boolean canUseAlone,
                                      final boolean isDiscountPercentage,
                                      final int amount) {
        validateAmountRange(isDiscountPercentage, amount);
        return Coupon.builder()
                .description(new Description(name, content))
                .policy(new Policy(canUseAlone, isDiscountPercentage, amount))
                .build();
    }

    private static void validateAmountRange(final boolean isDiscountPercentage, final int amount) {
        if (isDiscountPercentage && (amount <= MINIMUM_PERCENTAGE_DISCOUNT_AMOUNT || amount > MAXIMUM_PERCENTAGE_DISCOUNT_AMOUNT)) {
            throw new CouponAmountRangeInvalidException();
        }
    }

    public boolean isUsingAloneCoupon() {
        return this.policy.isCanUseAlone();
    }

    public boolean isPercentageCoupon() {
        return this.policy.isDiscountPercentage();
    }

    public int discount(final Integer price) {
        return this.policy.discount(price);
    }
}
