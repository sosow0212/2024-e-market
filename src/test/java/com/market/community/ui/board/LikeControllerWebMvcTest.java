package com.market.community.ui.board;

import com.market.helper.MockBeanInjection;
import com.market.member.domain.auth.TokenProvider;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.market.helper.RestDocsHelper.customDocument;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureRestDocs
@WebMvcTest(LikeController.class)
class LikeControllerWebMvcTest extends MockBeanInjection {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenProvider tokenProvider;

    @Test
    void 좋아요_버튼을_누른다() throws Exception {
        // given
        String token = "Bearer " + tokenProvider.create(1L);
        when(likeService.patchLike(anyLong(), anyLong())).thenReturn(true);

        // when & then
        mockMvc.perform(patch("/api/boards/{boardId}/likes", 1L)
                        .header(AUTHORIZATION, token)
                ).andExpect(status().isOk())
                .andDo(customDocument("patch_like",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("인증 토큰")
                        ),
                        pathParameters(
                                parameterWithName("boardId").description("게시글 id")
                        ),
                        responseFields(
                                fieldWithPath("boardId").description("게시글 id"),
                                fieldWithPath("likeStatus").description("현재 좋아요 상태 (true: 눌려있음, false: 안 눌려있음)")
                        )
                ));
    }
}
