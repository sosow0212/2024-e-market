package com.server.market.domain.chat;

import com.server.global.domain.BaseEntity;
import com.server.market.domain.chat.vo.ChattingStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "chatting_room")
public class ChattingRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Long buyerId;

    @Column(nullable = false)
    private Long sellerId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ChattingStatus chattingStatus;

    public static ChattingRoom createNewChattingRoom(
            final Long productId,
            final Long buyerId,
            final Long sellerId
    ) {
        return ChattingRoom.builder()
                .productId(productId)
                .buyerId(buyerId)
                .sellerId(sellerId)
                .chattingStatus(ChattingStatus.PROCESS)
                .build();
    }

    public void done() {
        this.chattingStatus = ChattingStatus.DONE;
    }
}
