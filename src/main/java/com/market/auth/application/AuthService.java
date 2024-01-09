package com.market.auth.application;

import com.market.auth.application.dto.LoginRequest;
import com.market.auth.application.dto.SignupRequest;
import com.market.auth.domain.TokenProvider;
import com.market.member.domain.Member;
import com.market.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;

    @Transactional
    public String signup(final SignupRequest request) {
        Member member = Member.createDefaultRole(request.email(), request.password(), request.nickname());

        Member signupMember = memberRepository.findByEmail(request.email())
                .orElseGet(() -> memberRepository.save(member));

        return tokenProvider.create(signupMember.getId());
    }

    @Transactional(readOnly = true)
    public String login(final LoginRequest request) {
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("로그인 이메일 존재x"));

        if (!member.getPassword().equals(request.password())) {
            throw new IllegalArgumentException("로그인 패스워드 틀림");
        }

        return tokenProvider.create(member.getId());
    }

    @Transactional(readOnly = true)
    public String test(final Long id) {
        return id + " 유저 정상 로그인 됨 ㅎㅇ";
    }
}
