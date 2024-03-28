package com.server.member.infrastructure.member;

import com.server.helper.IntegrationHelper;
import com.server.market.domain.product.Product;
import com.server.market.domain.product.ProductRepository;
import com.server.member.domain.member.Member;
import com.server.member.domain.member.MemberRepository;
import com.server.member.domain.member.TradeHistory;
import com.server.member.domain.member.TradeHistoryRepository;
import com.server.member.domain.member.dto.TradeHistoryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.server.market.fixture.ProductFixture.상품_생성;
import static com.server.member.fixture.member.MemberFixture.일반_유저_생성;
import static com.server.member.fixture.member.MemberFixture.일반_유저_생성2;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class TradeHistoryQueryRepositoryTest extends IntegrationHelper {

    @Autowired
    private TradeHistoryQueryRepository tradeHistoryQueryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TradeHistoryRepository tradeHistoryRepository;

    private Member seller;
    private Member buyer;
    private Product product;

    @BeforeEach
    void setup() {
        seller = memberRepository.save(일반_유저_생성());
        buyer = memberRepository.save(일반_유저_생성2());
        product = productRepository.save(상품_생성());
        tradeHistoryRepository.save(new TradeHistory(buyer.getId(), seller.getId(), product.getId(), product.getPrice().getPrice(), 10, List.of()));
    }

    @Test
    void 구매자의_구매_내역을_조회한다() {
        // when
        List<TradeHistoryResponse> result = tradeHistoryQueryRepository.findTradeHistories(buyer.getId(), false);

        // then
        assertSoftly(softly -> {
            softly.assertThat(result).hasSize(1);
            softly.assertThat(result.get(0).buyerName()).isEqualTo(buyer.getNickname());
            softly.assertThat(result.get(0).sellerName()).isEqualTo(seller.getNickname());
            softly.assertThat(result.get(0).productTitle()).isEqualTo(product.getDescription().getTitle());
        });
    }

    @Test
    void 판매자의_판매_내역을_조회한다() {
        // when
        List<TradeHistoryResponse> result = tradeHistoryQueryRepository.findTradeHistories(seller.getId(), true);

        // then
        assertSoftly(softly -> {
            softly.assertThat(result).hasSize(1);
            softly.assertThat(result.get(0).buyerName()).isEqualTo(buyer.getNickname());
            softly.assertThat(result.get(0).sellerName()).isEqualTo(seller.getNickname());
            softly.assertThat(result.get(0).productTitle()).isEqualTo(product.getDescription().getTitle());
        });
    }
}
