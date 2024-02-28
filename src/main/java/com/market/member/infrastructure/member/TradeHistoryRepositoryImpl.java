package com.market.member.infrastructure.member;

import com.market.member.domain.member.TradeHistory;
import com.market.member.domain.member.TradeHistoryRepository;
import com.market.member.domain.member.dto.TradeHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class TradeHistoryRepositoryImpl implements TradeHistoryRepository {

    private final TradeHistoryJpaRepository tradeHistoryJpaRepository;
    private final TradeHistoryQueryRepository tradeHistoryQueryRepository;

    @Override
    public TradeHistory save(final TradeHistory tradeHistory) {
        return tradeHistoryJpaRepository.save(tradeHistory);
    }

    @Override
    public List<TradeHistoryResponse> findHistories(final Long memberId, final boolean isSeller) {
        return tradeHistoryQueryRepository.findTradeHistories(memberId, isSeller);
    }
}
