package com.market.member.fixture.member;

import com.market.member.domain.member.TradeHistory;

import java.util.List;

public class TradeHistoryFixture {

    public static TradeHistory 거래_내역_생성() {
        return new TradeHistory(
                1L,
                2L,
                1L,
                10000,
                1000,
                List.of(1L, 2L, 3L)
        );
    }
}
