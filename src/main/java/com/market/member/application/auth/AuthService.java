package com.market.member.application.auth;

import com.market.member.application.auth.dto.LoginRequest;
import com.market.member.application.auth.dto.SignupRequest;
import com.market.member.domain.auth.TokenProvider;
import com.market.member.domain.member.Member;
import com.market.member.domain.member.MemberRepository;
import com.market.member.exception.exceptions.member.MemberAlreadyExistedException;
import com.market.member.exception.exceptions.member.MemberNotFoundException;
import com.market.member.exception.exceptions.member.PasswordNotMatchedException;
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
        validateExistedMember(request.email());
        Member signupMember = memberRepository.save(member);
        return tokenProvider.create(signupMember.getId());
    }

    private void validateExistedMember(final String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new MemberAlreadyExistedException();
        }
    }

    @Transactional(readOnly = true)
    public String login(final LoginRequest request) {
        Member member = findMemberByEmail(request.email());
        validatePassword(request.password(), member);

        return tokenProvider.create(member.getId());
    }

    private Member findMemberByEmail(final String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);
    }

    private void validatePassword(final String password, final Member member) {
        if (!member.hasSamePassword(password)) {
            throw new PasswordNotMatchedException();
        }
    }
}
