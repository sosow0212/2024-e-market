package com.market.member.infrastructure.member;

import com.market.member.domain.member.Member;
import com.market.member.domain.member.MemberRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemberFakeRepository implements MemberRepository {

    private final Map<Long, Member> map = new HashMap<>();
    private Long id = 0L;

    @Override
    public Optional<Member> findById(final Long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Optional<Member> findByNickname(final String nickname) {
        return map.values().stream()
                .filter(member -> member.getNickname().equals(nickname))
                .findAny();
    }

    @Override
    public Optional<Member> findByEmail(final String email) {
        return map.values().stream()
                .filter(member -> member.getEmail().equals(email))
                .findAny();
    }

    @Override
    public Member save(final Member member) {
        id++;

        Member saved = Member.builder()
                .id(id)
                .email(member.getEmail())
                .password(member.getPassword())
                .nickname(member.getNickname())
                .memberRole(member.getMemberRole())
                .build();

        map.put(id, saved);

        return saved;
    }

    @Override
    public boolean existsByEmail(final String email) {
        return map.values().stream()
                .anyMatch(member -> member.getEmail().equals(email));
    }
}
