package com.market.community.ui.board;

import com.market.community.domain.board.Board;
import com.market.member.domain.auth.TokenProvider;
import com.market.member.domain.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.market.community.fixture.BoardFixture.게시글_생성_사진없음;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class BoardControllerAcceptanceTest extends BoardControllerAcceptanceFixture {

    private static final String 게시글_생성_url = "/api/boards";
    private static final String 게시글_조회_url = "/api/boards/1";
    private static final String 게시글_수정_url = "/api/boards/1";
    private static final String 게시글_삭제_url = "/api/boards/1";

    @Autowired
    private TokenProvider provider;

    private Board 게시글;
    private Member 멤버;
    private String 멤버_토큰;

    @BeforeEach
    void setup() {
        게시글 = 게시글_생성_사진없음();
        멤버 = 멤버_생성();
        멤버_토큰 = provider.create(멤버.getId());
    }

    @Test
    void 게시글을_저장한다() {
        // given
        var 게시글_생성_요청 = 게시글_생성_요청서();

        // when
        var 게시글_생성_요청_결과 = 게시글을_저장한다(게시글_생성_요청, 게시글_생성_url, 멤버_토큰);

        // then
        게시글_생성_검증(게시글_생성_요청_결과);
    }

    @Test
    void 게시글을_단건_조회한다() {
        // given
        var 생성된_게시글 = 게시글을_생성한다(게시글);

        // when
        var 조회_요청_결과 = 게시글을_단건_조회한다(게시글_조회_url, 멤버_토큰);

        // then
        게시글_조회_검증(조회_요청_결과, 생성된_게시글);
    }

    @Test
    void 게시글을_수정한다() {
        // given
        게시글을_생성한다(게시글);
        var 게시글_수정_요청서 = 게시글_수정_요청서();

        // when
        var 수정_요청_결과 = 게시글을_수정한다(게시글_수정_요청서, 게시글_수정_url, 멤버_토큰);

        // then
        수정_결과_검증(수정_요청_결과, 게시글_수정_요청서);
    }

    @Test
    void 게시글을_삭제한다() {
        // given
        게시글을_생성한다(게시글);

        // when
        var 게시글_삭제_요청_결과 = 게시글을_삭제한다(게시글_삭제_url, 멤버_토큰);

        // then
        삭제_결과_검증(게시글_삭제_요청_결과);
    }
}
