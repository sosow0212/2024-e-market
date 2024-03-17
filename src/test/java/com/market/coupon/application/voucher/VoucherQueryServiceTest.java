package com.market.coupon.application.voucher;

import com.market.coupon.domain.coupon.Coupon;
import com.market.coupon.domain.coupon.CouponRepository;
import com.market.coupon.domain.voucher.Voucher;
import com.market.coupon.domain.voucher.VoucherRepository;
import com.market.coupon.domain.voucher.dto.VoucherSimpleResponse;
import com.market.coupon.domain.voucher.dto.VoucherSpecificResponse;
import com.market.helper.IntegrationHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static com.market.coupon.fixture.coupon.CouponFixture.쿠픈_생성_독자_사용_할인율_10_퍼센트;
import static com.market.coupon.fixture.voucher.VoucherFixture.바우처_1인용_생성;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class VoucherQueryServiceTest extends IntegrationHelper {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    private Coupon coupon;
    private Voucher voucher;

    @BeforeEach
    void setup() {
        coupon = couponRepository.save(쿠픈_생성_독자_사용_할인율_10_퍼센트());
        voucher = voucherRepository.save(바우처_1인용_생성());
    }

    @Test
    void 바우처를_페이징_조회한다() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<VoucherSimpleResponse> result = voucherRepository.findAllWithPaging(pageRequest);

        // then
        assertSoftly(softly -> {
            softly.assertThat(result.getContent()).hasSize(1);
            softly.assertThat(result.getContent().get(0).id()).isEqualTo(voucher.getId());
            softly.assertThat(result.getContent().get(0).couponId()).isEqualTo(coupon.getId());
            softly.assertThat(result.hasNext()).isEqualTo(false);
        });
    }

    @Test
    void 바우처를_상세_조회한다() {
        // given
        Long voucherId = voucher.getId();

        // when
        Optional<VoucherSpecificResponse> result = voucherRepository.findSpecificVoucherById(voucherId);

        // then
        assertSoftly(softly -> {
            softly.assertThat(result).isPresent();
            softly.assertThat(result.get().voucherId()).isEqualTo(voucherId);
            softly.assertThat(result.get().couponId()).isEqualTo(coupon.getId());
            softly.assertThat(result.get().voucherNumber()).isEqualTo(voucher.getVoucherNumber());
            softly.assertThat(result.get().isPublicVoucher()).isEqualTo(voucher.getIsPublic());
            softly.assertThat(result.get().discountAmount()).isEqualTo(coupon.policy.getAmount());
            softly.assertThat(result.get().isDiscountPercentageCoupon()).isEqualTo(coupon.policy.isDiscountPercentage());

        });
    }
}
