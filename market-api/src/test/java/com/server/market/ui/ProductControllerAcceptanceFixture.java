package com.server.market.ui;

import com.server.helper.IntegrationHelper;
import com.server.market.application.ProductService;
import com.server.market.application.dto.ProductCreateRequest;
import com.server.market.application.dto.ProductUpdateRequest;
import com.server.market.application.dto.UsingCouponRequest;
import com.server.market.domain.category.CategoryRepository;
import com.server.market.domain.product.Product;
import com.server.market.domain.product.ProductRepository;
import com.server.market.domain.product.dto.ProductSpecificResponse;
import com.server.market.domain.product.vo.Location;
import com.server.member.domain.auth.TokenProvider;
import com.server.member.domain.member.MemberRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.server.market.fixture.CategoryFixture.카테고리_생성;
import static com.server.market.fixture.ProductFixture.상품_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class ProductControllerAcceptanceFixture extends IntegrationHelper {

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected TokenProvider tokenProvider;

    @Autowired
    protected CategoryRepository categoryRepository;

    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected ProductService productService;

    @BeforeEach
    void initCategory() {
        categoryRepository.save(카테고리_생성());
    }

    protected ProductCreateRequest 상품_생성서_요청() {
        return new ProductCreateRequest("title", "content", 1000, Location.BUILDING_CENTER);
    }

    protected ExtractableResponse<Response> 상품_생성_요청(final String url, final String token, final ProductCreateRequest request) {
        return RestAssured.given().log().all()
                .body(request)
                .contentType(ContentType.JSON)
                .header(AUTHORIZATION, "Bearer " + token)
                .when()
                .post(url)
                .then()
                .extract();
    }

    protected void 상품_생성_검증(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    protected List<Product> 상품들을_생성한다() {
        Product savedProduct = productRepository.save(상품_생성());
        return List.of(savedProduct);
    }

    protected ExtractableResponse<Response> 상품을_조회한다(final String url, final String token) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .when()
                .get(url)
                .then()
                .extract();
    }

    protected void 상품_조회를_검증한다(final ExtractableResponse<Response> actual, final List<Product> fixture) {
        assertSoftly(softly -> {
            softly.assertThat(actual.statusCode()).isEqualTo(HttpStatus.OK.value());
        });
    }

    protected void 상품_단건_조회를_검증한다(final ExtractableResponse<Response> actual, final List<Product> fixture) {
        ProductSpecificResponse result = actual.as(ProductSpecificResponse.class);

        assertSoftly(softly -> {
            softly.assertThat(actual.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(result.id()).isEqualTo(fixture.get(0).getId());
        });
    }

    protected ProductUpdateRequest 상품_수정서를_요청한다() {
        return new ProductUpdateRequest("newTitle", "newContent", 10, 1L, Location.BUILDING_CENTER);
    }

    protected ExtractableResponse<Response> 상품을_수정한다(final String url, final String token, final ProductUpdateRequest request) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .body(request)
                .contentType(ContentType.JSON)
                .when()
                .patch(url)
                .then()
                .extract();
    }

    protected void 상품_수정_결과를_검증한다(final ExtractableResponse<Response> actual) {
        assertThat(actual.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    protected ExtractableResponse<Response> 상품을_제거한다(final String url, final String token) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .when()
                .delete(url)
                .then()
                .extract();
    }

    protected void 상품_제거_검증(final ExtractableResponse<Response> actual) {
        assertThat(actual.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    protected UsingCouponRequest 쿠폰_사용_요청서() {
        return new UsingCouponRequest(List.of(), 10000, 1000);
    }

    protected ExtractableResponse<Response> 상품_구매_결과(final UsingCouponRequest couponRequest, final String token) {
        return RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(couponRequest)
                .when()
                .post("/api/categories/1/products/1")
                .then().log().all()
                .extract();
    }

    protected void 상품_구매_검증(final ExtractableResponse response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
