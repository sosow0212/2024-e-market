package com.server.community.ui.board;

import com.server.community.domain.board.Board;
import com.server.community.domain.board.BoardRepository;
import com.server.helper.IntegrationHelper;
import com.server.member.domain.auth.TokenProvider;
import com.server.member.domain.member.MemberRepository;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class LikeControllerAcceptanceFixture extends IntegrationHelper {

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected BoardRepository boardRepository;

    @Autowired
    protected TokenProvider tokenProvider;

    protected ExtractableResponse 좋아요_버튼을_누른다(final String url, final String token) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .when()
                .patch(url)
                .then()
                .log().all().extract();
    }

    protected void 검증한다(final ExtractableResponse actual, final Board board) {
        Board result = boardRepository.findById(board.getId()).get();

        assertSoftly(softly -> {
            softly.assertThat(actual.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(result.getLikeCount().getLikeCount()).isEqualTo(1);
        });
    }
}
