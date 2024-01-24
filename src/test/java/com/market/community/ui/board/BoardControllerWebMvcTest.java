package com.market.community.ui.board;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.community.application.board.dto.BoardCreateRequest;
import com.market.community.application.board.dto.BoardUpdateRequest;
import com.market.community.domain.board.Board;
import com.market.helper.MockBeanInjection;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.market.community.fixture.BoardFixture.게시글_생성_사진없음_id있음;
import static com.market.helper.RestDocsHelper.customDocument;
import static org.apache.http.HttpHeaders.LOCATION;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureRestDocs
@WebMvcTest(BoardController.class)
class BoardControllerWebMvcTest extends MockBeanInjection {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 게시글을_저장한다() throws Exception {
        // given
        MockMultipartFile image = new MockMultipartFile("test1", "test1.PNG", MediaType.IMAGE_PNG_VALUE, "test1".getBytes());
        List<MultipartFile> imageFiles = List.of(image);

        BoardCreateRequest request = new BoardCreateRequest("title", "content", imageFiles);
        when(boardService.saveBoard(anyLong(), eq(request))).thenReturn(1L);

        // when & then
        mockMvc.perform(multipart("/api/boards")
                        .file("images", imageFiles.get(0).getBytes())
                        .param("title", request.title())
                        .param("content", request.content())
                        .with(requestPostProcessor -> {
                            requestPostProcessor.setMethod("POST");
                            return requestPostProcessor;
                        }).header(AUTHORIZATION, "Bearer tokenInfo ~~")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(customDocument("save_board",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("인증 토큰")
                        ),
                        requestParts(
                                partWithName("images").description("이미지 파일").optional(),
                                partWithName("title").description("수정한 제목").optional(),
                                partWithName("content").description("수정한 내용").optional()
                        ),
                        responseHeaders(headerWithName(LOCATION).description("리다이렉션 위치"))
                ));
    }

    @Test
    void 게시글을_단건_조회한다() throws Exception {
        // given
        Board board = 게시글_생성_사진없음_id있음();
        when(boardService.findBoardById(anyLong())).thenReturn(board);

        // when & then
        mockMvc.perform(get("/api/boards/{id}", board.getId()))
                .andExpect(status().isOk())
                .andDo(customDocument("find_board_by_id",
                        pathParameters(
                                parameterWithName("id").description("게시글 id")
                        ),
                        responseFields(
                                fieldWithPath("boardId").description("게시글 id"),
                                fieldWithPath("title").description("게시글 제목"),
                                fieldWithPath("content").description("게시글 내용"),
                                fieldWithPath("memberId").description("작성자 id")
                        )
                ));
    }

    @Test
    void 게시글을_수정한다() throws Exception {
        // given
        Board board = 게시글_생성_사진없음_id있음();
        MockMultipartFile image = new MockMultipartFile("test1", "test1.PNG", MediaType.IMAGE_PNG_VALUE, "test1".getBytes());
        BoardUpdateRequest request = new BoardUpdateRequest("수정 제목", "수정 내용", List.of(image), List.of(1L, 2L));
        doNothing().when(boardService).patchBoardById(board.getId(), board.getWriterId(), request);

        // when & then
        mockMvc.perform(multipart("/api/boards/{id}", board.getId())
                        .file("addedImages", request.addedImages().get(0).getBytes())
                        .param("title", request.title())
                        .param("content", request.content())
                        .param("deletedImages", StringUtils.collectionToCommaDelimitedString(request.deletedImages()))
                        .with(requestPostProcessor -> {
                            requestPostProcessor.setMethod("PATCH");
                            return requestPostProcessor;
                        }).header(AUTHORIZATION, "Bearer tokenInfo ~~")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(customDocument("patch_board",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("인증 토큰")
                        ),
                        pathParameters(
                                parameterWithName("id").description("게시글 id")
                        ),
                        requestParts(
                                partWithName("addedImages").description("새로 추가한 이미지 파일").optional(),
                                partWithName("deletedImages").description("삭제할 이미지 ID 목록").optional(),
                                partWithName("title").description("수정한 제목").optional(),
                                partWithName("content").description("수정한 내용").optional()
                        )
                ));
    }

    @Test
    void 게시글을_id로_제거한다() throws Exception {
        // given
        Long boardId = 1L;
        doNothing().when(boardService).deleteBoardById(eq(boardId), anyLong());

        // when & then
        mockMvc.perform(delete("/api/boards/{id}", boardId)
                        .header(AUTHORIZATION, "Bearer tokenInfo ~~"))
                .andExpect(status().isNoContent())
                .andDo(customDocument("delete_board",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("인증 토큰")
                        ),
                        pathParameters(
                                parameterWithName("id").description("게시글 id")
                        )
                ));
    }
}