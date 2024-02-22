package com.market.member.ui.member;

import com.market.member.application.member.MemberService;
import com.market.member.domain.member.TradeHistory;
import com.market.member.ui.auth.support.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{memberId}/histories")
    public ResponseEntity<Void> findTradeHistories(@PathVariable("memberId") final Long memberId,
                                                   @AuthMember final Long authId,
                                                   @RequestParam(value = "isSeller") final boolean isSeller) {
        // TODO : 거래내역 조회 구현 (join)
        List<TradeHistory> histories = memberService.findTradeHistories(memberId, authId, isSeller);
        return null;
    }
}
