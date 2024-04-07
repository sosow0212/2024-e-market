package com.server.coupon.domain.voucher;

import com.server.coupon.exception.exceptions.VoucherAlreadyUsedException;
import com.server.coupon.exception.exceptions.VoucherNumbersNotEqualsException;
import com.server.global.domain.BaseEntity;
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
public class Voucher extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long couponId;

    private String description;

    private String voucherNumber;

    private Boolean isPublic;

    private Boolean isUsed;

    public static Voucher createPrivate(final Long couponId, final String description, final VoucherNumberGenerator generator) {
        return Voucher.builder()
                .couponId(couponId)
                .description(description)
                .voucherNumber(generator.generate())
                .isPublic(false)
                .isUsed(false)
                .build();
    }

    public void use(final String voucherNumber) {
        validateNumberEquals(voucherNumber);
        validateAlreadyUsed();

        if (!this.isPublic) {
            this.isUsed = true;
        }
    }

    private void validateAlreadyUsed() {
        if (this.isUsed) {
            throw new VoucherAlreadyUsedException();
        }
    }

    private void validateNumberEquals(final String voucherNumber) {
        if (!this.voucherNumber.equals(voucherNumber)) {
            throw new VoucherNumbersNotEqualsException();
        }
    }
}
