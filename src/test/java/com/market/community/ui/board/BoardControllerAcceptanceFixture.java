package com.market.community.ui.board;

import com.market.community.application.board.dto.BoardCreateRequest;
import com.market.community.application.board.dto.BoardFoundResponse;
import com.market.community.application.board.dto.BoardUpdateRequest;
import com.market.community.domain.board.Board;
import com.market.community.domain.board.BoardRepository;
import com.market.helper.IntegrationHelper;
import com.market.member.domain.member.Member;
import com.market.member.domain.member.MemberRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

import static com.market.member.fixture.member.MemberFixture.일반_유저_생성;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class BoardControllerAcceptanceFixture extends IntegrationHelper {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    protected Member 멤버_생성() {
        return memberRepository.save(일반_유저_생성());
    }

    protected Board 게시글을_생성한다(final Board board) {
        return boardRepository.save(board);
    }

    protected BoardCreateRequest 게시글_생성_요청서() {
        return new BoardCreateRequest("title", "content", new ArrayList<MultipartFile>());
    }

    protected ExtractableResponse 게시글을_저장한다(final BoardCreateRequest request, final String url, final String token) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .param("title", request.title())
                .param("content", request.content())
                .when()
                .config(RestAssured.config().encoderConfig(encoderConfig().encodeContentTypeAs("multipart/form-data", ContentType.MULTIPART)))
                .post(url)
                .then().log().all()
                .extract();
    }

    protected void 게시글_생성_검증(final ExtractableResponse actual) {
        int code = actual.statusCode();
        String location = actual.header("Location");

        assertSoftly(softly -> {
            softly.assertThat(code).isEqualTo(HttpStatus.CREATED.value());
            softly.assertThat(location).isEqualTo("/api/boards/1");
        });
    }

    protected ExtractableResponse 게시글을_단건_조회한다(final String url, final String token) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .when()
                .get(url)
                .then().log().all()
                .extract();
    }

    protected void 게시글_조회_검증(final ExtractableResponse actual, final Board board) {
        int code = actual.statusCode();
        BoardFoundResponse result = actual.as(BoardFoundResponse.class);

        assertSoftly(softly -> {
            softly.assertThat(code).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(result.id()).isEqualTo(1L);
            softly.assertThat(result.title()).isEqualTo(board.getPost().getTitle());
            softly.assertThat(result.content()).isEqualTo(board.getPost().getContent());
            softly.assertThat(result.isMyPost()).isTrue();
        });
    }

    protected BoardUpdateRequest 게시글_수정_요청서() {
        return new BoardUpdateRequest("editTitle", "editContent", new ArrayList<>(), new ArrayList<>());
    }

    protected ExtractableResponse 게시글을_수정한다(final BoardUpdateRequest request, final String url, final String token) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .param("title", request.title())
                .param("content", request.content())
                .param("deletedImages", request.deletedImages())
                .when()
                .config(RestAssured.config().encoderConfig(encoderConfig().encodeContentTypeAs("multipart/form-data", ContentType.MULTIPART)))
                .patch(url)
                .then().log().all()
                .extract();
    }

    protected void 수정_결과_검증(final ExtractableResponse actual, final BoardUpdateRequest request) {
        int code = actual.statusCode();

        assertSoftly(softly -> {
            softly.assertThat(code).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(request.title()).isEqualTo("editTitle");
            softly.assertThat(request.content()).isEqualTo("editContent");
        });
    }

    protected ExtractableResponse 게시글을_삭제한다(final String url, final String token) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .when()
                .delete(url)
                .then().log().all()
                .extract();
    }

    protected void 삭제_결과_검증(final ExtractableResponse actual) {
        int code = actual.statusCode();

        assertThat(code).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}

