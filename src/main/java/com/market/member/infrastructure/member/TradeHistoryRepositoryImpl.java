package com.market.member.infrastructure.member;

import com.market.member.domain.member.TradeHistory;
import com.market.member.domain.member.TradeHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class TradeHistoryRepositoryImpl implements TradeHistoryRepository {

    private final TradeHistoryJpaRepository tradeHistoryJpaRepository;

    @Override
    public TradeHistory save(final TradeHistory tradeHistory) {
        return tradeHistoryJpaRepository.save(tradeHistory);
    }

    @Override
    public List<TradeHistory> findAllByBuyerId(final Long buyerId) {
        return tradeHistoryJpaRepository.findAllByBuyerId(buyerId);
    }

    @Override
    public List<TradeHistory> findAllBySellerId(final Long sellerId) {
        return tradeHistoryJpaRepository.findAllBySellerId(sellerId);
    }
}
