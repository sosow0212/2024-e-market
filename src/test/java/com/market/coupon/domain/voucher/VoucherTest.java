package com.market.coupon.domain.voucher;

import com.market.coupon.exception.exceptions.VoucherAlreadyUsedException;
import com.market.coupon.exception.exceptions.VoucherNumbersNotEqualsException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.market.coupon.fixture.voucher.VoucherFixture.바우처_1인용_생성;
import static com.market.coupon.fixture.voucher.VoucherFixture.바우처_프로모션용_재사용_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class VoucherTest {

    @Nested
    class 바우처_사용 {

        @Test
        void private_바우처를_사용한다() {
            // given
            Voucher voucher = 바우처_1인용_생성();

            // when
            voucher.use(voucher.getVoucherNumber());

            // then
            assertThat(voucher.getIsUsed()).isTrue();
        }

        @Test
        void public_바우처를_사용한다() {
            // given
            Voucher voucher = 바우처_프로모션용_재사용_생성();

            // when
            voucher.use(voucher.getVoucherNumber());

            // then
            assertThat(voucher.getIsUsed()).isFalse();
        }

        @Test
        void 바우처의_번호가_다르면_사용할_수_없다() {
            // given
            Voucher voucher = 바우처_1인용_생성();

            // when & then
            assertThatThrownBy(() -> voucher.use("wrong"))
                    .isInstanceOf(VoucherNumbersNotEqualsException.class);
        }

        @Test
        void 사용한_1인용_바우처는_사용할_수_없다() {
            // given
            Voucher voucher = 바우처_1인용_생성();
            voucher.use(voucher.getVoucherNumber());

            // when & then
            assertThatThrownBy(() -> voucher.use(voucher.getVoucherNumber()))
                    .isInstanceOf(VoucherAlreadyUsedException.class);
        }
    }
}
