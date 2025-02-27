package com.server.member.infrastructure.member;

import com.server.member.domain.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static com.server.member.fixture.member.MemberFixture.일반_유저_생성;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
class MemberJpaRepositoryTest {

    @Autowired
    private MemberJpaRepository memberRepository;

    private Member member;

    @BeforeEach
    void setup() {
        member = 일반_유저_생성();
        memberRepository.save(member);
    }

    @DisplayName("멤버를 찾는다")
    @Nested
    class FindMember {

        @Test
        void 아이디_값으로_멤버를_찾는다() {
            // when
            Optional<Member> result = memberRepository.findById(member.getId());

            // then
            assertSoftly(softly -> {
                softly.assertThat(result).isPresent();
                softly.assertThat(result.get()).usingRecursiveComparison().isEqualTo(member);
            });
        }

        @Test
        void 닉네임_값으로_멤버를_찾는다() {
            // when
            Optional<Member> result = memberRepository.findByNickname(member.getNickname());

            // then
            assertSoftly(softly -> {
                softly.assertThat(result).isPresent();
                softly.assertThat(result.get()).usingRecursiveComparison().isEqualTo(member);
            });
        }

        @Test
        void 이메일_값으로_멤버를_찾는다() {
            // when
            Optional<Member> result = memberRepository.findByEmail(member.getEmail());

            // then
            assertSoftly(softly -> {
                softly.assertThat(result).isPresent();
                softly.assertThat(result.get()).usingRecursiveComparison().isEqualTo(member);
            });
        }
    }
}
