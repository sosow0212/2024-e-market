package com.server.coupon.infrastructure.voucher;

import com.server.helper.IntegrationHelper;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class VoucherNumberUUIDGeneratorImplTest extends IntegrationHelper {

    @Autowired
    private VoucherNumberUUIDGeneratorImpl voucherNumberUUIDGenerator;

    @Test
    void 번호는_한_필드에_6개씩_총_4개의_필드로_이루어진다() {
        // given
        int delimiterSize = 3;
        int numberSize = 24;

        // when
        String numbers = voucherNumberUUIDGenerator.generate();

        // then
        String[] fields = numbers.split("-");
        assertSoftly(softly -> {
            softly.assertThat(fields.length).isEqualTo(4);
            softly.assertThat(fields[0].length()).isEqualTo(6);
            softly.assertThat(numbers.length()).isEqualTo(delimiterSize + numberSize);
        });
    }
}
