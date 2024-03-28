package com.server.community.fixture;

import com.server.community.domain.board.Image;

public class ImageFixture {

    public static Image 이미지를_생성한다() {
        return Image.builder()
                .id(1L)
                .originName("origin")
                .uniqueName("unique")
                .build();
    }
}
