package com.server.market.infrastructure.chat;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.server.market.domain.chat.dto.ChatHistoryResponse;
import com.server.market.domain.chat.dto.ChattingRoomSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.querydsl.core.types.Projections.constructor;
import static com.server.market.domain.chat.QChat.chat;
import static com.server.market.domain.chat.QChattingRoom.chattingRoom;
import static com.server.market.domain.product.QProduct.product;
import static com.server.member.domain.member.QMember.member;

@RequiredArgsConstructor
@Repository
public class ChatQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<ChattingRoomSimpleResponse> findMyChattingRooms(final Long authId) {
        return jpaQueryFactory.select(constructor(ChattingRoomSimpleResponse.class,
                        product.description.title,
                        product.id,
                        chattingRoom.id,
                        chattingRoom.sellerId,
                        member.nickname,
                        chattingRoom.createdAt
                )).from(chattingRoom)
                .join(product).on(chattingRoom.productId.eq(product.id))
                .join(member).on(chattingRoom.sellerId.eq(member.id))
                .where(chattingRoom.buyerId.eq(authId).or(chattingRoom.sellerId.eq(authId)))
                .fetch();
    }

    public List<ChatHistoryResponse> findChattingHistoryByChatId(
            final Long authId,
            final Long chattingRoomId,
            final Long chatId,
            final Integer pageSize
    ) {
        return jpaQueryFactory.select(constructor(ChatHistoryResponse.class,
                        chat.chatRoomId,
                        chat.id,
                        chat.senderId,
                        member.nickname,
                        chat.message,
                        chat.senderId.eq(authId),
                        chat.createdAt
                )).from(chat)
                .leftJoin(member).on(member.id.eq(chat.senderId))
                .where(
                        chat.chatRoomId.eq(chattingRoomId),
                        ltChatId(chatId)
                )
                .orderBy(chat.createdAt.desc())
                .limit(pageSize)
                .fetch();
    }

    private BooleanExpression ltChatId(final Long chatId) {
        if (chatId == null) {
            return null;
        }

        return chat.id.lt(chatId);
    }
}
