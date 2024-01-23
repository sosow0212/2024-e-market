package com.market.market.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class ProductControllerAcceptanceTest extends ProductControllerAcceptanceFixture {

    private static final String 상품_생성_url = "/api/categories/1/products";
    private static final String 카테고리에_포함된_상품_조회_url = "/api/categories/1/products";
    private static final String 상품_상세조회_url = "/api/categories/1/products/1";
    private static final String 상품_수정_url = "/api/categories/1/products/1";
    private static final String 상품_제거_url = "/api/categories/1/products/1";

    private String 토큰;

    @BeforeEach
    void setup() {
        super.initCategory();
        토큰 = tokenProvider.create(1L);
    }

    @Test
    void 상품을_등록한다() {
        // given
        var 상품_생성서 = 상품_생성서_요청();

        // when
        var 상품_생성_요청_결과 = 상품_생성_요청(상품_생성_url, 토큰, 상품_생성서);

        // then
        상품_생성_검증(상품_생성_요청_결과);
    }

    @Test
    void 카테고리에_포함된_상품을_모두_조회한다() {
        // given
        var 상품들_1번_카테고리_해당 = 상품들을_생성한다();

        // when
        var 카테고리에_해당하는_상품들_조회_결과 = 상품을_조회한다(카테고리에_포함된_상품_조회_url, 토큰);

        // then
        상품_조회를_검증한다(카테고리에_해당하는_상품들_조회_결과, 상품들_1번_카테고리_해당);
    }

    @Test
    void 상품을_상세_조회한다() {
        // given
        var 상품들_1번_카테고리_해당 = 상품들을_생성한다();

        // when
        var 상품_조회_결과 = 상품을_조회한다(상품_상세조회_url, 토큰);

        // then
        상품_단건_조회를_검증한다(상품_조회_결과, 상품들_1번_카테고리_해당);
    }

    @Test
    void 상품을_수정한다() {
        // given
        상품들을_생성한다();
        var 상품_수정서 = 상품_수정서를_요청한다();

        // when
        var 상품_수정_결과 = 상품을_수정한다(상품_수정_url, 토큰, 상품_수정서);

        // then
        상품_수정_결과를_검증한다(상품_수정_결과);
    }

    @Test
    void 상품을_제거한다() {
        // given
        상품들을_생성한다();

        // when
        var 상품_제거_결과 = 상품을_제거한다(상품_제거_url, 토큰);

        // then
        상품_제거_검증(상품_제거_결과);
    }
}
