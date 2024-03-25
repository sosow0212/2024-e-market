package com.market.community.ui.comment;

import com.market.community.application.comment.dto.CommentCreateRequest;
import com.market.community.application.comment.dto.CommentPatchRequest;
import com.market.community.domain.comment.Comment;
import com.market.community.domain.comment.CommentRepository;
import com.market.helper.IntegrationHelper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class CommentAcceptanceFixture extends IntegrationHelper {

    @Autowired
    private CommentRepository commentRepository;

    protected Comment 댓글을_생성한다(final Comment comment) {
        return commentRepository.save(comment);
    }

    protected CommentCreateRequest 댓글_생성_요청서() {
        return new CommentCreateRequest("댓글 내용");
    }

    protected ExtractableResponse 댓글을_저장한다(final CommentCreateRequest request, final String url, final String token) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(request)
                .post(url)
                .then().log().all()
                .extract();
    }

    protected void 댓글_생성_검증(final ExtractableResponse actual) {
        assertThat(actual.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    protected ExtractableResponse 게시글에_포함된_댓글을_모두_조회한다(final String url, final String token) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .accept(ContentType.JSON)
                .when()
                .get(url)
                .then().log().all()
                .extract();
    }

    protected void 댓글_조회_검증(final ExtractableResponse actual, final Comment comment) {
        assertSoftly(softly -> {
            softly.assertThat(actual.statusCode()).isEqualTo(HttpStatus.OK.value());
        });
    }

    protected CommentPatchRequest 댓글_수정_요청서_생성() {
        return new CommentPatchRequest("댓글 수정");
    }

    protected ExtractableResponse 댓글을_수정한다(final CommentPatchRequest request, final String url, final String token) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .patch(url)
                .then().log().all()
                .extract();
    }

    protected void 댓글_수정_검증(final ExtractableResponse actual) {
        assertThat(actual.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    protected ExtractableResponse 댓글을_삭제한다(final String url, final String token) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .when()
                .delete(url)
                .then().log().all()
                .extract();
    }

    protected void 댓글_삭제_검증(final ExtractableResponse actual) {
        assertThat(actual.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
