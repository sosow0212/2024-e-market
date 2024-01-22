package com.market.board.ui;

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
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
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
                        )
                ));
    }
}
