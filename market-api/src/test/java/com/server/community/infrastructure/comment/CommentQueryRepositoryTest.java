package com.server.community.infrastructure.comment;

import com.server.community.domain.board.Board;
import com.server.community.domain.board.BoardRepository;
import com.server.community.domain.comment.Comment;
import com.server.community.domain.comment.CommentRepository;
import com.server.community.domain.comment.dto.CommentSimpleResponse;
import com.server.helper.IntegrationHelper;
import com.server.member.domain.member.Member;
import com.server.member.domain.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.server.community.fixture.BoardFixture.게시글_생성_사진없음;
import static com.server.member.fixture.member.MemberFixture.일반_유저_생성;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class CommentQueryRepositoryTest extends IntegrationHelper {

    @Autowired
    private CommentQueryRepository commentQueryRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BoardRepository boardRepository;

    private Board board;
    private Member member;

    @BeforeEach
    void setup() {
        member = memberRepository.save(일반_유저_생성());
        board = boardRepository.save(게시글_생성_사진없음());
    }

    @Test
    void no_offset_페이징_첫_조회() {
        // given
        for (long i = 1L; i <= 20L; i++) {
            commentRepository.save(Comment.builder()
                    .id(i)
                    .boardId(board.getId())
                    .content("comment")
                    .writerId(member.getId())
                    .build()
            );
        }

        // when
        List<CommentSimpleResponse> result = commentQueryRepository.findAllWithPaging(1L, member.getId(), null, 10);

        // then
        assertSoftly(softly -> {
            softly.assertThat(result).hasSize(10);
            softly.assertThat(result.get(0).id()).isEqualTo(20L);
            softly.assertThat(result.get(9).id()).isEqualTo(11L);
        });
    }

    @Test
    void no_offset_페이징_두번째_조회() {
        // given
        for (long i = 1L; i <= 20L; i++) {
            commentRepository.save(Comment.builder()
                    .id(i)
                    .boardId(board.getId())
                    .content("comment")
                    .writerId(member.getId())
                    .build()
            );
        }

        // when
        List<CommentSimpleResponse> result = commentQueryRepository.findAllWithPaging(1L, member.getId(), 11L, 10);

        // then
        assertSoftly(softly -> {
            softly.assertThat(result).hasSize(10);
            softly.assertThat(result.get(0).id()).isEqualTo(10L);
            softly.assertThat(result.get(9).id()).isEqualTo(1L);
        });
    }
}
