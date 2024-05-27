package com.server.market.application.chat.dto;

import jakarta.validation.constraints.NotNull;

public record ChattingRoomCreateRequest(
        @NotNull(message = "판매자 id가 들어와야 합니다.")
        Long sellerId
) {
}
