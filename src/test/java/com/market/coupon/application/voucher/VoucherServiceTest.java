package com.market.coupon.application.voucher;

import com.market.coupon.application.voucher.dto.VoucherCreateRequest;
import com.market.coupon.application.voucher.dto.VoucherNumberRequest;
import com.market.coupon.domain.voucher.Voucher;
import com.market.coupon.domain.voucher.VoucherNumberGenerator;
import com.market.coupon.domain.voucher.VoucherRepository;
import com.market.coupon.exception.exceptions.VoucherAlreadyUsedException;
import com.market.coupon.exception.exceptions.VoucherNotFoundException;
import com.market.coupon.exception.exceptions.VoucherNumbersNotEqualsException;
import com.market.coupon.infrastructure.voucher.FakeVoucherNumberGenerator;
import com.market.coupon.infrastructure.voucher.FakeVoucherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.market.coupon.fixture.voucher.VoucherFixture.바우처_1인용_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class VoucherServiceTest {

    private VoucherRepository voucherRepository;
    private VoucherNumberGenerator voucherNumberGenerator;
    private VoucherService voucherService;

    @BeforeEach
    void setup() {
        voucherRepository = new FakeVoucherRepository();
        voucherNumberGenerator = new FakeVoucherNumberGenerator();
        voucherService = new VoucherService(voucherRepository, voucherNumberGenerator);
    }

    @Test
    void 바우처를_생성한다() {
        // given
        VoucherCreateRequest request = new VoucherCreateRequest(1L, "설명");
        Voucher expected = Voucher.builder()
                .id(1L)
                .isUsed(false)
                .isPublic(false)
                .voucherNumber("number")
                .description("설명")
                .couponId(1L)
                .build();

        // when
        Voucher voucher = voucherService.savePrivateVoucher(request);

        // then
        assertThat(voucher)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Nested
    class 바우처_사용 {

        @Test
        void 바우처를_사용한다() {
            // given
            Voucher voucher = voucherRepository.save(바우처_1인용_생성());

            // when
            voucherService.useVoucher(voucher.getId(), new VoucherNumberRequest(voucher.getVoucherNumber()), 1L);

            // then
            assertThat(voucher.getIsUsed()).isTrue();
        }

        @Test
        void 바우처가_없다면_예외를_발생시킨다() {
            // when & then
            assertThatThrownBy(() -> voucherService.useVoucher(-1L, new VoucherNumberRequest("number"), 1L))
                    .isInstanceOf(VoucherNotFoundException.class);

        }

        @Test
        void 이미_사용한_바우처를_사용하면_예외를_발생시킨다() {
            // given
            Voucher voucher = voucherRepository.save(바우처_1인용_생성());
            voucher.use(voucher.getVoucherNumber());

            // when & then
            assertThatThrownBy(() -> voucherService.useVoucher(voucher.getId(), new VoucherNumberRequest(voucher.getVoucherNumber()), 1L))
                    .isInstanceOf(VoucherAlreadyUsedException.class);
        }

        @Test
        void 바우처_번호가_다르면_예외를_발생시킨다() {
            // given
            Voucher voucher = voucherRepository.save(바우처_1인용_생성());

            // when & then
            assertThatThrownBy(() -> voucherService.useVoucher(voucher.getId(), new VoucherNumberRequest("wrong"), 1L))
                    .isInstanceOf(VoucherNumbersNotEqualsException.class);
        }
    }
}
