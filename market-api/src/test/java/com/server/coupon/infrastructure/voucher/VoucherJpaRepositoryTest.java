package com.server.coupon.infrastructure.voucher;

import com.server.coupon.domain.voucher.Voucher;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static com.server.coupon.fixture.voucher.VoucherFixture.바우처_1인용_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
class VoucherJpaRepositoryTest {

    @Autowired
    private VoucherJpaRepository voucherJpaRepository;

    @Test
    void 바우처를_저장한다() {
        // given
        Voucher voucher = 바우처_1인용_생성();

        // when
        Voucher saved = voucherJpaRepository.save(voucher);

        // then
        assertThat(voucher)
                .usingRecursiveComparison()
                .ignoringFields("id", "createdAt", "updatedAt")
                .isEqualTo(saved);
    }

    @Test
    void 바우처를_단건_조회한다() {
        // given
        Voucher saved = voucherJpaRepository.save(바우처_1인용_생성());

        // when
        Optional<Voucher> found = voucherJpaRepository.findById(saved.getId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(found).isPresent();
            softly.assertThat(found.get())
                    .usingRecursiveComparison()
                    .isEqualTo(saved);
        });
    }
}
