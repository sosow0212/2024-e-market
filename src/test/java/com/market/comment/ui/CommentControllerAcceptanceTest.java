package com.market.comment.ui;

import com.market.comment.domain.Comment;
import com.market.member.domain.auth.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.market.comment.fixture.CommentFixture.댓글_생성;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class CommentControllerAcceptanceTest extends CommentAcceptanceFixture {

    private static final String 댓글_생성_url = "/api/boards/1/comments";
    private static final String 댓글_조회_url = "/api/boards/1/comments";
    private static final String 댓글_수정_url = "/api/boards/1/comments/1";
    private static final String 댓글_삭제_url = "/api/boards/1/comments/1";
    private static final String 게시글_삭제_url = "/api/boards/1";

    @Autowired
    private TokenProvider tokenProvider;

    private String 멤버_토큰;
    private Comment 댓글;

    @BeforeEach
    void setup() {
        멤버_토큰 = tokenProvider.create(1L);
        댓글 = 댓글_생성();
    }

    @Test
    void 댓글을_생성한다() {
        // given
        var 댓글_생성_요청 = 댓글_생성_요청서();

        // when
        var 댓글_생성_요청_결과 = 댓글을_저장한다(댓글_생성_요청, 댓글_생성_url, 멤버_토큰);

        // then
        댓글_생성_검증(댓글_생성_요청_결과);
    }

    @Test
    void 댓글을_모두_조회한다() {
        // given
        var 생성된_댓글 = 댓글을_생성한다(댓글);

        // when
        var 댓글_조회_결과 = 게시글에_포함된_댓글을_모두_조회한다(댓글_조회_url, 멤버_토큰);

        // then
        댓글_조회_검증(댓글_조회_결과, 생성된_댓글);
    }

    @Test
    void 댓글을_수정한다() {
        // given
        댓글을_저장한다(댓글_생성_요청서(), 댓글_생성_url, 멤버_토큰);
        var 댓글_수정_요청서 = 댓글_수정_요청서_생성();

        // when
        var 댓글_수정_결과 = 댓글을_수정한다(댓글_수정_요청서, 댓글_수정_url, 멤버_토큰);

        // then
        댓글_수정_검증(댓글_수정_결과);
    }

    @Test
    void 댓글을_제거한다() {
        // given
        댓글을_저장한다(댓글_생성_요청서(), 댓글_생성_url, 멤버_토큰);

        // when
        var 댓글_삭제_결과 = 댓글을_삭제한다(댓글_삭제_url, 멤버_토큰);

        // then
        댓글_삭제_검증(댓글_삭제_결과);
    }
}
