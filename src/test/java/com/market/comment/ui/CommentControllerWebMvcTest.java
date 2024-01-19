package com.market.comment.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.comment.application.dto.CommentCreateRequest;
import com.market.comment.application.dto.CommentPatchRequest;
import com.market.comment.domain.Comment;
import com.market.helper.MockBeanInjection;
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

import static com.market.comment.fixture.CommentFixture.댓글_생성;
import static com.market.helper.RestDocsHelper.customDocument;
import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
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
@WebMvcTest(CommentController.class)
class CommentControllerWebMvcTest extends MockBeanInjection {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 댓글을_생성한다() throws Exception {
        // given
        Long boardId = 1L;
        CommentCreateRequest req = new CommentCreateRequest("comment");
        doNothing().when(commentService).createComment(eq(boardId), anyLong(), eq(req));

        // when & then
        mockMvc.perform(post("/api/boards/{boardId}/comments", boardId)
                        .header(AUTHORIZATION, "Bearer tokenInfo~")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req))
                ).andExpect(status().isCreated())
                .andDo(customDocument("save_comment",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
                        ),
                        pathParameters(
                                parameterWithName("boardId").description("게시글 id")
                        ),
                        requestFields(
                                fieldWithPath("comment").description("작성할 댓글 내용")
                        )
                ));
    }

    @Test
    void 게시글의_댓글을_모두_조회한다() throws Exception {
        // given
        Long boardId = 1L;
        Comment comment = 댓글_생성();
        when(commentService.findAllCommentsByBoardId(boardId)).thenReturn(List.of(comment));

        // when & then
        mockMvc.perform(get("/api/boards/{boardId}/comments", boardId)
                        .header(AUTHORIZATION, "Bearer tokenInfo~")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andDo(customDocument("find_all_comments",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
                        ),
                        pathParameters(
                                parameterWithName("boardId").description("게시글 id")
                        ),
                        responseFields(
                                fieldWithPath("comments[0].commentId").description("댓글 id"),
                                fieldWithPath("comments[0].boardId").description("게시글 id"),
                                fieldWithPath("comments[0].writerId").description("작성자 id"),
                                fieldWithPath("comments[0].comment").description("댓글 내용")
                        )
                ));
    }

    @Test
    void 댓글을_수정한다() throws Exception {
        // given
        Long boardId = 1L;
        Long commentId = 1L;
        CommentPatchRequest request = new CommentPatchRequest("수정된 댓글");
        doNothing().when(commentService).patchCommentById(anyLong(), anyLong(), eq(request));

        // when & then
        mockMvc.perform(patch("/api/boards/{boardId}/comments/{commentId}", boardId, commentId)
                        .header(AUTHORIZATION, "Bearer tokenInfo~")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isOk())
                .andDo(customDocument("patch_comment",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
                        ),
                        pathParameters(
                                parameterWithName("boardId").description("게시글 id"),
                                parameterWithName("commentId").description("수정할 댓글 id")
                        ),
                        requestFields(
                                fieldWithPath("comment").description("수정할 댓글 내용")
                        )
                ));
    }

    @Test
    void 댓글을_지운다() throws Exception {
        // given
        Long boardId = 1L;
        Long commentId = 1L;
        doNothing().when(commentService).deleteCommentById(anyLong(), anyLong());

        // when & then
        mockMvc.perform(delete("/api/boards/{boardId}/comments/{commentId}", boardId, commentId)
                        .header(AUTHORIZATION, "Bearer tokenInfo~")
                ).andExpect(status().isNoContent())
                .andDo(customDocument("delete_comment",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
                        ),
                        pathParameters(
                                parameterWithName("boardId").description("게시글 id"),
                                parameterWithName("commentId").description("수정할 댓글 id")
                        )
                ));
    }
}
