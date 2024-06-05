package com.server.member.application.member;

import com.server.member.application.member.dto.TradeHistoryCreateRequest;
import com.server.member.domain.member.Member;
import com.server.member.domain.member.MemberRepository;
import com.server.member.domain.member.TradeHistory;
import com.server.member.domain.member.TradeHistoryRepository;
import com.server.member.domain.member.dto.ProductByMemberResponse;
import com.server.member.domain.member.dto.TradeHistoryResponse;
import com.server.member.exception.exceptions.member.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final TradeHistoryRepository tradeHistoryRepository;

    public List<TradeHistoryResponse> findTradeHistories(final Long memberId, final Long authId, final boolean isSeller) {
        Member member = findMember(authId);
        member.validateAuth(memberId);
        return tradeHistoryRepository.findHistories(memberId, isSeller);
    }

    private Member findMember(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }

    @Transactional
    public Long saveTradeHistory(final TradeHistoryCreateRequest request) {
        TradeHistory tradeHistory = new TradeHistory(
                request.buyerId(),
                request.sellerId(),
                request.productId(),
                request.productOriginPrice(),
                request.productDiscountPrice(),
                request.usingCouponIds()
        );

        TradeHistory savedTradeHistory = tradeHistoryRepository.save(tradeHistory);
        return savedTradeHistory.getId();
    }

    @Transactional(readOnly = true)
    public List<ProductByMemberResponse> findProductHistories(final Long memberId, final Long authId) {
        Member member = findMember(memberId);
        member.validateAuth(memberId);
        return memberRepository.findProductsByMemberId(memberId);
    }
}
