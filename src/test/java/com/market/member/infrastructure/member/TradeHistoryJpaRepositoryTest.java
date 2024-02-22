package com.market.member.infrastructure.member;

import com.market.member.domain.member.TradeHistory;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.market.member.fixture.member.TradeHistoryFixture.거래_내역_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
class TradeHistoryJpaRepositoryTest {

    @Autowired
    private TradeHistoryJpaRepository tradeHistoryJpaRepository;

    @Test
    void 거래_내역을_저장한다() {
        // given
        TradeHistory tradeHistory = 거래_내역_생성();

        // when
        TradeHistory result = tradeHistoryJpaRepository.save(tradeHistory);

        // then
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(tradeHistory);
    }

    @Test
    void 구매자_id로_거래내역을_모두_찾는다() {
        // given
        TradeHistory tradeHistory = tradeHistoryJpaRepository.save(거래_내역_생성());

        // when
        List<TradeHistory> result = tradeHistoryJpaRepository.findAllByBuyerId(tradeHistory.getBuyerId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(result).hasSize(1);
            softly.assertThat(result.get(0))
                    .usingRecursiveComparison()
                    .ignoringFields("id")
                    .isEqualTo(tradeHistory);
        });
    }

    @Test
    void 판매자_id로_거래내역을_모두_찾는다() {
        // given
        TradeHistory tradeHistory = tradeHistoryJpaRepository.save(거래_내역_생성());

        // when
        List<TradeHistory> result = tradeHistoryJpaRepository.findAllBySellerId(tradeHistory.getSellerId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(result).hasSize(1);
            softly.assertThat(result.get(0))
                    .usingRecursiveComparison()
                    .ignoringFields("id")
                    .isEqualTo(tradeHistory);
        });
    }
}
