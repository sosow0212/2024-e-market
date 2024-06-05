package com.server.member.domain.member;

import com.server.member.domain.member.dto.ProductByMemberResponse;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Optional<Member> findById(Long id);

    Optional<Member> findByNickname(String nickname);

    Optional<Member> findByEmail(String email);

    Member save(Member member);

    boolean existsByEmail(String email);

    List<ProductByMemberResponse> findProductsByMemberId(Long memberId);
}
