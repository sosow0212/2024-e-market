package com.server.market.ui.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.helper.MockBeanInjection;
import com.server.market.application.product.dto.ProductCreateRequest;
import com.server.market.application.product.dto.ProductUpdateRequest;
import com.server.market.domain.product.dto.ProductPagingSimpleResponse;
import com.server.market.domain.product.dto.ProductSpecificResponse;
import com.server.market.domain.product.vo.Location;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.server.helper.RestDocsHelper.customDocument;
import static com.server.market.fixture.ProductFixture.상품_상세정보_생성;
import static com.server.market.fixture.ProductFixture.상품_페이징_생성;
import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureRestDocs
@WebMvcTest(ProductController.class)
class ProductControllerWebMvcTest extends MockBeanInjection {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 카테고리에_해당되는_상품들을_조회한다_페이징() throws Exception {
        // given
        Long categoryId = 1L;
        List<ProductPagingSimpleResponse> response = List.of(상품_페이징_생성());

        when(productQueryService.findAllProductsInCategory(anyLong(), anyLong(), anyInt())).thenReturn(response);

        // when & then
        mockMvc.perform(get("/api/categories/{categoryId}/products", categoryId)
                        .header(AUTHORIZATION, "Bearer tokenInfo~")
                        .param("productId", "11")
                        .param("pageSize", "10")
                ).andExpect(status().isOk())
                .andDo(customDocument("find_all_products_in_category",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
                        ),
                        pathParameters(
                                parameterWithName("categoryId").description("카테고리 id (필수)")
                        ),
                        queryParameters(
                                parameterWithName("productId").description("마지막으로 받은 product Id, 맨 처음 조회라면 null 허용"),
                                parameterWithName("pageSize").description("한 페이지에 조회되는 사이즈")
                        ),
                        responseFields(
                                fieldWithPath("[].id").description("상품 id"),
                                fieldWithPath("[].location").description("거래 장소 (3공학관, 5공학관 등등..)"),
                                fieldWithPath("[].title").description("상품 제목"),
                                fieldWithPath("[].price").description("상품 가격"),
                                fieldWithPath("[].visitedCount").description("상품 조회수"),
                                fieldWithPath("[].contactCount").description("구매자가 판매자에게 건 채팅 수"),
                                fieldWithPath("[].productStatus").description("상품 상태 (WAITING, RESERVED, COMPLETED)"),
                                fieldWithPath("[].ownerName").description("판매자 닉네임"),
                                fieldWithPath("[].createDate").description("상품 판매 등록일")
                        )
                ));
    }

    @Test
    void 상품을_등록한다() throws Exception {
        // given
        Long categoryId = 1L;
        ProductCreateRequest request = new ProductCreateRequest("title", "content", 1000, Location.BUILDING_CENTER);

        // when & then
        mockMvc.perform(post("/api/categories/{categoryId}/products", categoryId)
                        .header(AUTHORIZATION, "Bearer tokenInfo~")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isCreated())
                .andDo(customDocument("upload_product",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
                        ),
                        pathParameters(
                                parameterWithName("categoryId").description("카테고리 id")
                        ),
                        requestFields(
                                fieldWithPath("location").description("거래 장소(BUILDING_THREE, BUILDING_FIVE, BUILDING_LIBRARY, BUILDING_CENTER, NEAR_MJU)"),
                                fieldWithPath("title").description("상품 제목"),
                                fieldWithPath("content").description("상품 설명"),
                                fieldWithPath("price").description("상품 가격")
                        ),
                        responseHeaders(
                                headerWithName("LOCATION").description("리다이렉션 url")
                        )
                ));
    }

    @Test
    void 상품_단건_조회를_한다() throws Exception {
        // given
        Long categoryId = 1L;
        Long productId = 1L;
        ProductSpecificResponse response = 상품_상세정보_생성();
        when(productQueryService.findById(any(), any())).thenReturn(response);

        // when & then
        mockMvc.perform(get("/api/categories/{categoryId}/products/{productId}", categoryId, productId)
                        .header(AUTHORIZATION, "Bearer tokenInfo~")
                        .cookie(new Cookie("productView", "[1]")))
                .andExpect(status().isOk())
                .andDo(customDocument("find_product_by_id",
                        requestCookies(
                                cookieWithName("productView").description("방문한 Product Id들 (조회수 체킹용)")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
                        ),
                        pathParameters(
                                parameterWithName("categoryId").description("카테고리 id"),
                                parameterWithName("productId").description("조회하는 상품 id")
                        ),
                        responseFields(
                                fieldWithPath("id").description("상품 id"),
                                fieldWithPath("location").description("거래 장소 (3공학관, 5공학관 등등..)"),
                                fieldWithPath("title").description("상품 제목"),
                                fieldWithPath("content").description("상품 내용"),
                                fieldWithPath("price").description("상품 가격"),
                                fieldWithPath("productStatus").description("상품 상태 (WAITING, RESERVED, COMPLETED)"),
                                fieldWithPath("visitedCount").description("상품 조회자 수"),
                                fieldWithPath("contactCount").description("판매자에게 연락한 사람 수"),
                                fieldWithPath("categoryId").description("카테고리 id"),
                                fieldWithPath("categoryName").description("카테고리 이름"),
                                fieldWithPath("ownerNickname").description("판매자 닉네임"),
                                fieldWithPath("isMyProduct").description("자신이 등록한 상품인지 (Boolean)"),
                                fieldWithPath("createDate").description("상품 등록일")
                        )
                ));
    }

    @Test
    void 상품을_수정한다() throws Exception {
        // given
        Long categoryId = 1L;
        Long productId = 1L;
        ProductUpdateRequest request = new ProductUpdateRequest("newTitle", "newContent", 1000, 2L, Location.BUILDING_CENTER);
        doNothing().when(productService).update(anyLong(), anyLong(), eq(request));

        // when & then
        mockMvc.perform(patch("/api/categories/{categoryId}/products/{productId}", categoryId, productId)
                        .header(AUTHORIZATION, "Bearer tokenInfo~")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isOk())
                .andDo(customDocument("patch_product_by_id",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
                        ),
                        pathParameters(
                                parameterWithName("categoryId").description("카테고리 id"),
                                parameterWithName("productId").description("조회하는 상품 id")
                        ),
                        requestFields(
                                fieldWithPath("location").description("거래 장소(BUILDING_THREE, BUILDING_FIVE, BUILDING_LIBRARY, BUILDING_CENTER, NEAR_MJU)"),
                                fieldWithPath("title").description("업데이트할 상품명"),
                                fieldWithPath("content").description("업데이트할 상품 설명"),
                                fieldWithPath("price").description("업데이트할 가격"),
                                fieldWithPath("categoryId").description("업데이트할 카테고리 id")
                        )
                ));
    }

    @Test
    void 상품을_제거한다() throws Exception {
        // given
        Long categoryId = 1L;
        Long productId = 1L;
        doNothing().when(productService).delete(anyLong(), anyLong());

        // when & then
        mockMvc.perform(delete("/api/categories/{categoryId}/products/{productId}", categoryId, productId)
                        .header(AUTHORIZATION, "Bearer tokenInfo~")
                ).andExpect(status().isNoContent())
                .andDo(customDocument("delete_product_by_id",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
                        ),
                        pathParameters(
                                parameterWithName("categoryId").description("카테고리 id"),
                                parameterWithName("productId").description("조회하는 상품 id")
                        )
                ));
    }

    @Test
    void 상품을_좋아요_처리한다() throws Exception {
        // given
        Long categoryId = 1L;
        Long productId = 1L;
        when(productService.likes(anyLong(), anyLong())).thenReturn(true);

        // when & then
        mockMvc.perform(patch("/api/categories/{categoryId}/products/{productId}/likes", categoryId, productId)
                        .header(AUTHORIZATION, "Bearer tokenInfo~")
                ).andExpect(status().isOk())
                .andDo(customDocument("likes_product_by_id",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
                        ),
                        pathParameters(
                                parameterWithName("categoryId").description("카테고리 id"),
                                parameterWithName("productId").description("조회하는 상품 id")
                        )
                ));
    }
}
