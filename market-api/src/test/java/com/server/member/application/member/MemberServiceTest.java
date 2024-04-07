package com.server.member.application.member;

import com.server.member.application.member.dto.TradeHistoryCreateRequest;
import com.server.member.domain.member.Member;
import com.server.member.domain.member.MemberRepository;
import com.server.member.domain.member.TradeHistory;
import com.server.member.domain.member.TradeHistoryRepository;
import com.server.member.domain.member.dto.TradeHistoryResponse;
import com.server.member.fixture.member.MemberFixture;
import com.server.member.infrastructure.member.MemberFakeRepository;
import com.server.member.infrastructure.member.TradeHistoryFakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberServiceTest {

    private MemberRepository memberRepository;
    private TradeHistoryRepository tradeHistoryRepository;
    private MemberService memberService;

    @BeforeEach
    void setup() {
        memberRepository = new MemberFakeRepository();
        tradeHistoryRepository = new TradeHistoryFakeRepository();
        memberService = new MemberService(memberRepository, tradeHistoryRepository);
    }

    @Test
    void 거래_내역을_저장한다() {
        // given
        TradeHistoryCreateRequest request = new TradeHistoryCreateRequest(
                1L,
                2L,
                1L,
                10000,
                10,
                List.of()
        );

        // when
        Long result = memberService.saveTradeHistory(request);

        // then
        assertThat(result).isEqualTo(1L);
    }

    @Test
    void 거래_내역을_조회한다() {
        // given
        TradeHistory savedHistory = tradeHistoryRepository.save(
                new TradeHistory(
                        1L,
                        2L,
                        1L,
                        10000,
                        10,
                        List.of()
                )
        );

        Member saved = memberRepository.save(MemberFixture.일반_유저_생성());

        // when
        List<TradeHistoryResponse> buyerHistories = memberService.findTradeHistories(saved.getId(), saved.getId(), false);

        // then
        assertSoftly(softly -> {
            softly.assertThat(buyerHistories).hasSize(1);
            softly.assertThat(buyerHistories.get(0).buyerName()).isEqualTo("buyer");
        });
    }
}
