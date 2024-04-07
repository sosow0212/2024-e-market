package com.server.member.domain.member;

import com.server.member.domain.member.dto.TradeHistoryResponse;

import java.util.List;

public interface TradeHistoryRepository {

    TradeHistory save(TradeHistory tradeHistory);

    List<TradeHistoryResponse> findHistories(Long memberId, boolean isSeller);
}
