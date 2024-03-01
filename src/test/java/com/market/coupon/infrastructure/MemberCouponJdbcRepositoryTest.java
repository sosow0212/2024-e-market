package com.market.coupon.infrastructure;

import com.market.coupon.domain.MemberCoupon;
import com.market.helper.IntegrationHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.market.coupon.fixture.MemberCouponFixture.멤버_쿠폰_생성;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberCouponJdbcRepositoryTest extends IntegrationHelper {

    @Autowired
    private MemberCouponJdbcRepository memberCouponJdbcRepository;

    @Test
    void 멤버_쿠폰을_벌크_저장한다() {
        // given
        MemberCoupon memberCoupon = 멤버_쿠폰_생성();

        // when & then
        Assertions.assertDoesNotThrow(() -> memberCouponJdbcRepository.insertBulk(List.of(memberCoupon, memberCoupon, memberCoupon)));
    }
}
