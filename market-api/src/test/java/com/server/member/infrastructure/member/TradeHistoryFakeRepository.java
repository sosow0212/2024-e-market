package com.server.member.infrastructure.member;

import com.server.member.domain.member.TradeHistory;
import com.server.member.domain.member.TradeHistoryRepository;
import com.server.member.domain.member.dto.TradeHistoryResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TradeHistoryFakeRepository implements TradeHistoryRepository {

    private final Map<Long, TradeHistory> map = new HashMap<>();
    private Long id = 0L;

    @Override
    public TradeHistory save(final TradeHistory tradeHistory) {
        id++;

        TradeHistory savedHistory = TradeHistory.builder()
                .id(id)
                .sellerId(tradeHistory.getSellerId())
                .buyerId(tradeHistory.getBuyerId())
                .productId(tradeHistory.getBuyerId())
                .productOriginPrice(tradeHistory.getProductOriginPrice())
                .productDiscountPrice(tradeHistory.getProductDiscountPrice())
                .usingCouponIds(tradeHistory.getUsingCouponIds())
                .build();

        map.put(id, savedHistory);
        return savedHistory;
    }

    @Override
    public List<TradeHistoryResponse> findHistories(final Long memberId, final boolean isSeller) {
        List<TradeHistory> result = new ArrayList<>();

        if (isSeller) {
            result = map.values().stream()
                    .filter(it -> it.getSellerId().equals(memberId))
                    .toList();
        }

        if (!isSeller) {
            result = map.values().stream()
                    .filter(it -> it.getBuyerId().equals(memberId))
                    .toList();
        }

        if (result.isEmpty()) {
            return new ArrayList<>();
        }

        return result.stream()
                .map(it -> new TradeHistoryResponse(
                        it.getId(),
                        "buyer",
                        "seller",
                        "title",
                        it.getProductOriginPrice(),
                        it.getProductDiscountPrice(),
                        it.getUsingCouponIds()
                )).toList();
    }
}
