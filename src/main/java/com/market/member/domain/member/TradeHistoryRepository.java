package com.market.member.domain.member;

import java.util.List;

public interface TradeHistoryRepository {

    TradeHistory save(TradeHistory tradeHistory);

    List<TradeHistory> findAllByBuyerId(Long buyerId);

    List<TradeHistory> findAllBySellerId(Long sellerId);
}
