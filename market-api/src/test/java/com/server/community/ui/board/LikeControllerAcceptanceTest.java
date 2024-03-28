package com.server.community.ui.board;

import com.server.community.domain.board.Board;
import com.server.member.domain.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static com.server.community.fixture.BoardFixture.게시글_생성_사진없음;
import static com.server.member.fixture.member.MemberFixture.일반_유저_생성;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class LikeControllerAcceptanceTest extends LikeControllerAcceptanceFixture {

    private static final String 좋아요_url = "/api/boards/1/likes";

    private Board 게시글;
    private String 토큰;

    @BeforeEach
    void setup() {
        Member 멤버 = memberRepository.save(일반_유저_생성());
        게시글 = boardRepository.save(게시글_생성_사진없음());
        토큰 = tokenProvider.create(멤버.getId());
    }

    @Test
    void 게시글에_좋아요_버튼을_누른다() {
        // when
        var 좋아요_응답 = 좋아요_버튼을_누른다(좋아요_url, 토큰);

        // then
        검증한다(좋아요_응답, 게시글);
    }
}
