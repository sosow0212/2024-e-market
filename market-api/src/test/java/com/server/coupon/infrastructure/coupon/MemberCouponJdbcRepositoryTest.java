package com.server.coupon.infrastructure.coupon;

import com.server.coupon.domain.coupon.MemberCoupon;
import com.server.helper.IntegrationHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.server.coupon.fixture.coupon.MemberCouponFixture.멤버_쿠폰_생성;

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
