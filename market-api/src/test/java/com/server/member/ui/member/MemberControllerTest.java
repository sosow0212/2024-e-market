package com.server.member.ui.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.helper.MockBeanInjection;
import com.server.member.domain.member.dto.TradeHistoryResponse;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.server.helper.RestDocsHelper.customDocument;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureRestDocs
@WebMvcTest(MemberController.class)
class MemberControllerTest extends MockBeanInjection {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 거래_내역을_조회한다() throws Exception {
        // given
        when(memberService.findTradeHistories(anyLong(), anyLong(), anyBoolean()))
                .thenReturn(List.of(new TradeHistoryResponse(1L, "buyerNickname", "sellerNickname", "productTitle", 10000, 10, "1,2,3")));

        // when & then
        mockMvc.perform(get("/api/members/{memberId}/histories", 1L)
                        .queryParam("isSeller", String.valueOf(true))
                        .header(HttpHeaders.AUTHORIZATION, "Bearer tokenInfo~")
                ).andExpect(status().isOk())
                .andDo(customDocument("find_trade_histories",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
                        ),
                        queryParameters(
                                parameterWithName("isSeller").description("판매자인지 구매자인지 여부 (true = 판매자, false = 구매자)")
                        ),
                        responseFields(
                                fieldWithPath("[0].tradeHistoryId").description("거래 내역 id"),
                                fieldWithPath("[0].buyerName").description("구매자 닉네임"),
                                fieldWithPath("[0].sellerName").description("판매자 닉네임"),
                                fieldWithPath("[0].productTitle").description("상품 제목"),
                                fieldWithPath("[0].productOriginPrice").description("상품 정상가"),
                                fieldWithPath("[0].productDiscountPrice").description("상품 할인해서 구매한 가격"),
                                fieldWithPath("[0].usingCouponIds").description("사용한 쿠폰 ids, String 타입으로 ',' 이용해서 묶음")

                        )
                ));
    }

}
