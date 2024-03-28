package com.server.coupon.ui.voucher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.coupon.application.voucher.dto.VoucherCreateRequest;
import com.server.coupon.application.voucher.dto.VoucherNumberRequest;
import com.server.coupon.domain.voucher.Voucher;
import com.server.coupon.domain.voucher.dto.VoucherSimpleResponse;
import com.server.coupon.domain.voucher.dto.VoucherSpecificResponse;
import com.server.helper.MockBeanInjection;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static com.server.coupon.fixture.voucher.VoucherFixture.바우처_1인용_생성;
import static com.server.helper.RestDocsHelper.customDocument;
import static org.mockito.ArgumentMatchers.any;
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
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureRestDocs
@WebMvcTest(VoucherController.class)
class VoucherControllerWebMvcTest extends MockBeanInjection {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 바우처를_생성한다() throws Exception {
        // given
        Voucher voucher = 바우처_1인용_생성();
        VoucherCreateRequest request = new VoucherCreateRequest(voucher.getId(), voucher.getDescription());
        when(voucherService.savePrivateVoucher(eq(request))).thenReturn(voucher);

        // when & then
        mockMvc.perform(post("/api/vouchers")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer tokenInfo..")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andDo(customDocument("create_voucher",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
                        ),
                        requestFields(
                                fieldWithPath("couponId").description("바우처 발급할 때 필요한 쿠폰의 id"),
                                fieldWithPath("description").description("바우처 설명 (ex. 신규 회원 10% 할인)")
                        ),
                        responseHeaders(
                                headerWithName("LOCATION").description("리다이렉션 url")
                        )
                ));
    }

    @Test
    void 바우처를_사용한다() throws Exception {
        // given
        Long id = 1L;
        VoucherNumberRequest request = new VoucherNumberRequest("AAAA11-BB22BB-33CCCC-44DDDD");
        doNothing().when(voucherService).useVoucher(1L, request, 1L);

        // when & then
        mockMvc.perform(post("/api/vouchers/{voucherId}", id)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer tokenInfo..")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andDo(customDocument("use_voucher",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
                        ),
                        pathParameters(
                                parameterWithName("voucherId").description("바우처 id")
                        ),
                        requestFields(
                                fieldWithPath("voucherNumber").description("사용할 바우처의 number")
                        )
                ));
    }

    @Test
    void 바우처를_페이지로_조회한다() throws Exception {
        Voucher voucher = 바우처_1인용_생성();
        VoucherSimpleResponse voucherSimpleResponse = new VoucherSimpleResponse(voucher.getId(), 1L, "신규 회원 바우처", false, false, LocalDateTime.now());
        Page<VoucherSimpleResponse> response = new PageImpl<>(List.of(voucherSimpleResponse), PageRequest.of(0, 10), 1);
        when(voucherQueryService.findAllWithPaging(any(Pageable.class))).thenReturn(response);

        // when & then
        mockMvc.perform(get("/api/vouchers")
                        .param("page", "0")
                        .param("size", "10")
                        .header(AUTHORIZATION, "Bearer tokenInfo")
                ).andExpect(status().isOk())
                .andDo(customDocument("find_vouchers_with_paging",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
                        ),
                        requestParts(
                                partWithName("page").description("페이지 번호").optional(),
                                partWithName("size").description("몇 개씩 조회를 할 것인지").optional()
                        ),
                        responseFields(
                                fieldWithPath("vouchers[].id").description("바우처 id"),
                                fieldWithPath("vouchers[].couponId").description("바우처에 등록된 쿠폰 id"),
                                fieldWithPath("vouchers[].description").description("바우처 설명"),
                                fieldWithPath("vouchers[].isPublic").description("1인용 바우처(private) 혹은 프로모션용 다회용 바우처인지 여부(public)"),
                                fieldWithPath("vouchers[].isUsed").description("이미 사용한 바우처인지 여부"),
                                fieldWithPath("vouchers[].createDate").description("바우처 생성일"),
                                fieldWithPath("nextPage").description("다음 페이지가 존재하면 1, 없다면 -1")
                        )
                ));
    }

    @Test
    void 바우처_상세_조회() throws Exception {
        // given
        Long voucherId = 1L;
        VoucherSpecificResponse response = new VoucherSpecificResponse(
                voucherId,
                1L,
                "10% 할인 쿠폰",
                false,
                true,
                10,
                "신규 회원을 위한 10% 할인 쿠폰 바우처",
                "A12345-B12345-C12345-D12345",
                false,
                false,
                LocalDateTime.now()
        );

        when(voucherQueryService.findSpecificVoucher(voucherId)).thenReturn(response);

        // when & then
        mockMvc.perform(get("/api/vouchers/{voucherId}", voucherId)
                        .header(AUTHORIZATION, "Bearer tokenInfo")
                ).andExpect(status().isOk())
                .andDo(customDocument("find_voucher_specific",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
                        ),
                        pathParameters(
                                parameterWithName("voucherId").description("바우처 id")
                        ),
                        responseFields(
                                fieldWithPath("voucherId").description("바우처 id"),
                                fieldWithPath("couponId").description("바우처에 등록된 쿠폰 id"),
                                fieldWithPath("couponName").description("쿠폰 이름 (바우처에 해당되는 쿠폰의 설명)"),
                                fieldWithPath("canUseAloneCoupon").description("독자 사용 쿠폰인지 여부 (바우처에 해당되는 쿠폰의 설명)"),
                                fieldWithPath("isDiscountPercentageCoupon").description("할인률에 의한 할인인지 절대 금액 할인인지 여부 (바우처에 해당되는 쿠폰의 설명)"),
                                fieldWithPath("discountAmount").description("쿠폰 할인 값 (바우처에 해당되는 쿠폰의 설명)"),
                                fieldWithPath("voucherDescription").description("바우처 설명"),
                                fieldWithPath("voucherNumber").description("바우처 번호"),
                                fieldWithPath("isPublicVoucher").description("1인용 바우처(private) 혹은 프로모션용 다회용 바우처인지 여부(public)"),
                                fieldWithPath("isUsedVoucher").description("이미 사용한 바우처인지 여부"),
                                fieldWithPath("createDate").description("바우처 생성일")
                        )
                ));
    }
}
