package com.market.member.application.member;

import com.market.member.application.member.dto.TradeHistoryCreateRequest;
import com.market.member.domain.member.Member;
import com.market.member.domain.member.MemberRepository;
import com.market.member.domain.member.TradeHistory;
import com.market.member.domain.member.TradeHistoryRepository;
import com.market.member.exception.exceptions.member.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final TradeHistoryRepository tradeHistoryRepository;

    @Transactional(readOnly = true)
    public List<TradeHistory> findTradeHistories(final Long memberId, final Long authId, final boolean isSeller) {
        Member member = findMember(authId);
        member.validateAuth(memberId);
        return findHistories(memberId, isSeller);
    }

    private Member findMember(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }

    private List<TradeHistory> findHistories(final Long memberId, final boolean isSeller) {
        if (isSeller) {
            tradeHistoryRepository.findAllBySellerId(memberId);
        }

        return tradeHistoryRepository.findAllByBuyerId(memberId);
    }

    @Transactional
    public void saveTradeHistory(final TradeHistoryCreateRequest request) {
        TradeHistory tradeHistory = new TradeHistory(
                request.buyerId(),
                request.sellerId(),
                request.productId(),
                request.productOriginPrice(),
                request.productDiscountPrice(),
                request.usingCouponIds()
        );

        tradeHistoryRepository.save(tradeHistory);
    }
}
