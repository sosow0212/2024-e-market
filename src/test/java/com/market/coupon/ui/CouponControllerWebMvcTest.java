package com.market.coupon.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.coupon.application.dto.CouponCreateRequest;
import com.market.coupon.application.dto.MemberCouponCreateRequest;
import com.market.coupon.domain.Coupons;
import com.market.helper.MockBeanInjection;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.market.coupon.fixture.CouponFixture.쿠픈_생성_독자_사용_할인율_10_퍼센트;
import static com.market.helper.RestDocsHelper.customDocument;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureRestDocs
@WebMvcTest(CouponController.class)
class CouponControllerWebMvcTest extends MockBeanInjection {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 쿠폰을_새로_생성한다() throws Exception {
        // given
        CouponCreateRequest request = new CouponCreateRequest("쿠폰명", "쿠폰 내용", false, true, 10);

        // when & then
        mockMvc.perform(post("/api/coupons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isCreated())
                .andDo(customDocument("create_new_coupon",
                        requestFields(
                                fieldWithPath("name").description("생성할 쿠폰명"),
                                fieldWithPath("content").description("쿠폰 설명"),
                                fieldWithPath("canUseAlone").description("독립 사용 쿠폰 여부"),
                                fieldWithPath("isDiscountPercentage").description("확률성 쿠폰 혹은 금액 총량 쿠폰인지 여부"),
                                fieldWithPath("amount").description("쿠폰 할인 밸류")
                        ),
                        responseHeaders(
                                headerWithName("location").description("생성된 쿠폰 id")
                        )
                ));
    }

    @Test
    void 멤버가_가진_쿠폰을_모두_조회한다() throws Exception {
        // given
        Coupons response = new Coupons(List.of(쿠픈_생성_독자_사용_할인율_10_퍼센트()));
        when(couponService.findAllMemberCoupons(anyLong())).thenReturn(response);

        // when & then
        mockMvc.perform(get("/api/coupons/members")
                        .header(AUTHORIZATION, "Bearer tokenInfo~~"))
                .andExpect(status().isOk())
                .andDo(customDocument("find_all_member_coupons",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
                        ),
                        responseFields(
                                fieldWithPath("memberId").description("요청한 유저 id"),
                                fieldWithPath("coupons[0].couponId").description("쿠폰 id"),
                                fieldWithPath("coupons[0].name").description("쿠폰명"),
                                fieldWithPath("coupons[0].content").description("쿠폰 설명"),
                                fieldWithPath("coupons[0].canUseAlone").description("독립 사용 쿠폰인지의 여부"),
                                fieldWithPath("coupons[0].isDiscountPercentage").description("확률 할인 쿠폰인지의 여부"),
                                fieldWithPath("coupons[0].amount").description("할인 밸류")
                        )
                ));
    }

    @Test
    void 멤버에게_쿠폰을_준다() throws Exception {
        // given
        MemberCouponCreateRequest request = new MemberCouponCreateRequest(List.of(1L));
        doNothing().when(couponService).saveMemberCoupons(anyLong(), eq(request));

        // when & then
        mockMvc.perform(post("/api/coupons/members/{memberId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isOk())
                .andDo(customDocument("save_member_coupon",
                        pathParameters(
                                parameterWithName("memberId").description("member의 id")
                        ),
                        requestFields(
                                fieldWithPath("couponIds[0]").description("유저에게 줄 쿠폰 id")
                        )
                ));
    }
}
