package com.server.community.infrastructure.board;

import com.server.community.application.board.dto.BoardSimpleResponse;
import com.server.community.domain.board.Board;
import com.server.community.domain.board.BoardRepository;
import com.server.helper.IntegrationHelper;
import com.server.member.domain.member.Member;
import com.server.member.domain.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.server.community.fixture.BoardFixture.게시글_생성_사진없음;
import static com.server.member.fixture.member.MemberFixture.일반_유저_생성;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class BoardQueryRepositoryTest extends IntegrationHelper {

    @Autowired
    private BoardQueryRepository boardQueryRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    @BeforeEach
    void setup() {
        member = memberRepository.save(일반_유저_생성());
    }

    @Test
    void 게시글_15개에서_1번_페이지에서_10개의_게시글을_페이징_조회를_한다() {
        // given
        List<Board> boards = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            boards.add(boardRepository.save(게시글_생성_사진없음()));
        }

        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<BoardSimpleResponse> found = boardQueryRepository.findAllBoard(pageRequest, 1L);

        // then
        List<BoardSimpleResponse> expected = boards.stream()
                .sorted(Comparator.comparing(Board::getId).reversed())
                .limit(10)
                .map(it -> new BoardSimpleResponse(it.getId(), member.getNickname(), it.getPost().getTitle(), it.getCreatedAt(), 0L, 0L, false))
                .toList();

        assertSoftly(softly -> {
            softly.assertThat(found).hasSize(10);
            softly.assertThat(found.hasNext()).isTrue();
            softly.assertThat(found.getContent())
                    .usingRecursiveComparison()
                    .ignoringFieldsOfTypes(LocalDateTime.class)
                    .isEqualTo(expected);
        });
    }
}
