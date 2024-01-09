package com.market.member.infrastructure.member;

import com.market.member.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(final String email);

    Optional<Member> findByNickname(final String nickname);

    boolean existsByEmail(final String email);
}
