package com.server.member.infrastructure.member;

import com.server.member.domain.member.Member;
import com.server.member.domain.member.MemberRepository;
import com.server.member.domain.member.dto.ProductByMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;
    private final MemberQueryRepository memberQueryRepository;

    @Override
    public Optional<Member> findById(final Long id) {
        return memberJpaRepository.findById(id);
    }

    @Override
    public Optional<Member> findByNickname(final String nickname) {
        return memberJpaRepository.findByNickname(nickname);
    }

    @Override
    public Optional<Member> findByEmail(final String email) {
        return memberJpaRepository.findByEmail(email);
    }

    @Override
    public Member save(final Member member) {
        return memberJpaRepository.save(member);
    }

    @Override
    public boolean existsByEmail(final String email) {
        return memberJpaRepository.existsByEmail(email);
    }

    @Override
    public List<ProductByMemberResponse> findProductsByMemberId(final Long memberId) {
        return memberQueryRepository.findProductsByMemberId(memberId);
    }
}
