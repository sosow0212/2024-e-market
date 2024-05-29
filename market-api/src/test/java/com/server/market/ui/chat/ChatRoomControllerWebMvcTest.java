package com.server.market.ui.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.helper.MockBeanInjection;
import com.server.market.application.chat.ChatRoomQueryService;
import com.server.market.application.chat.dto.ChattingRoomCreateRequest;
import com.server.market.domain.chat.ChattingRoom;
import com.server.market.domain.chat.dto.ChatHistoryResponse;
import com.server.market.domain.chat.dto.ChattingRoomSimpleResponse;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static com.server.helper.RestDocsHelper.customDocument;
import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureRestDocs
@WebMvcTest(ChatRoomController.class)
class ChatRoomControllerWebMvcTest extends MockBeanInjection {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ChatRoomQueryService chatRoomQueryService;

    @Test
    void 사용자가_채팅중인_모든_채팅방을_반환한다() throws Exception {
        // given
        List<ChattingRoomSimpleResponse> response = List.of(new ChattingRoomSimpleResponse("상품명", 1L, 1L, 1L, "판매자_닉네임", LocalDateTime.now()));
        when(chatRoomQueryService.findAllMyChats(anyLong())).thenReturn(response);

        // when & then
        mockMvc.perform(get("/api/chats")
                        .header(AUTHORIZATION, "Bearer tokenInfo~")
                ).andExpect(status().isOk())
                .andDo(customDocument("find_all_my_chatting_rooms",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
                        ),
                        responseFields(
                                fieldWithPath("[].productName").description("채팅에 해당하는 상품명"),
                                fieldWithPath("[].productId").description("상품 id"),
                                fieldWithPath("[].chattingRoomId").description("채팅방 id"),
                                fieldWithPath("[].sellerId").description("판매자 id"),
                                fieldWithPath("[].sellerNickname").description("판매자 닉네임"),
                                fieldWithPath("[].lastChattingTime").description("채팅 시간")
                        )
                ));
    }

    @Test
    void 소비자가_채팅을_요청해서_생성한다() throws Exception {
        // given
        ChattingRoom chattingRoom = ChattingRoom.createNewChattingRoom(1L, 1L, 2L);
        when(chatRoomService.createChattingRoomByBuyer(anyLong(), anyLong(), anyLong())).thenReturn(chattingRoom);

        // when & then
        mockMvc.perform(post("/api/products/{productId}/chats", 1)
                        .header(AUTHORIZATION, "Bearer tokenInfo~")
                        .content(objectMapper.writeValueAsString(new ChattingRoomCreateRequest(2L)))
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andDo(customDocument("create_new_chatting_room",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
                        ),
                        pathParameters(
                                parameterWithName("productId").description("해당하는 상품 id")
                        ),
                        requestFields(
                                fieldWithPath("sellerId").description("상품 판매자 id")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("리다이렉션 uri (새로운 채팅방으로 입장)")
                        ),
                        responseFields(
                                fieldWithPath("chatRoomId").description("채팅방의 id"),
                                fieldWithPath("productId").description("상품 id"),
                                fieldWithPath("buyerId").description("구매자 id"),
                                fieldWithPath("sellerId").description("판매자 id"),
                                fieldWithPath("chattingStatus").description("채팅방 상태 (PROCESS / DONE) -> 거래가 완료되면 DONE으로 변경")
                        )
                ));
    }

    @Test
    void 채팅방_내역_반환() throws Exception {
        // given
        List<ChatHistoryResponse> response = List.of(new ChatHistoryResponse(1L, 1L, 1L, "구매자_닉네임", "흥정해주세요", true, LocalDateTime.now()));
        when(chatRoomQueryService.findChattingHistoryByChatId(anyLong(), anyLong(), anyLong(), any())).thenReturn(response);

        // when & then
        mockMvc.perform(get("/api/products/{productId}/chats/{chattingRoomId}", 1, 1)
                        .header(AUTHORIZATION, "Bearer tokenInfo~")
                        .param("chatId", "1")
                        .param("pageSize", "10")
                ).andExpect(status().isOk())
                .andDo(customDocument("find_chatting_history",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
                        ),
                        queryParameters(
                                parameterWithName("chatId").description("마지막으로 받은 chatId, 맨 처음 조회라면 null 허용"),
                                parameterWithName("pageSize").description("한 페이지에 조회되는 사이즈")
                        ),
                        pathParameters(
                                parameterWithName("productId").description("해당하는 상품 id"),
                                parameterWithName("chattingRoomId").description("채팅방 id (채팅 id와는 다름)")
                        ),
                        responseFields(
                                fieldWithPath("[].chatRoomId").description("채팅방의 id"),
                                fieldWithPath("[].chattingId").description("채팅 id"),
                                fieldWithPath("[].senderId").description("보낸 사람 id"),
                                fieldWithPath("[].senderNickname").description("보낸 사람 닉네임"),
                                fieldWithPath("[].message").description("전송한 채팅 내용"),
                                fieldWithPath("[].isSendByMe").description("자신이 보냈는지 여부"),
                                fieldWithPath("[].sendTime").description("채팅 전송 시간")
                        )
                ));
    }
}
