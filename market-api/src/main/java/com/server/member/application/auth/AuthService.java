package com.server.member.application.auth;

import com.server.global.event.Events;
import com.server.member.application.auth.dto.LoginRequest;
import com.server.member.application.auth.dto.SignupRequest;
import com.server.member.domain.auth.TokenProvider;
import com.server.member.domain.auth.event.RegisteredEvent;
import com.server.member.domain.member.Member;
import com.server.member.domain.member.MemberRepository;
import com.server.member.domain.member.NicknameGenerator;
import com.server.member.exception.exceptions.member.MemberAlreadyExistedException;
import com.server.member.exception.exceptions.member.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final NicknameGenerator nicknameGenerator;

    @Transactional
    public String signup(final SignupRequest request) {
        validateExistedMember(request.email());

        Member member = Member.createDefaultRole(request.email(), request.password(), nicknameGenerator);
        Member signupMember = memberRepository.save(member);

        Events.raise(new RegisteredEvent(member.getId(), member.getEmail(), member.getNickname()));
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
        member.validatePassword(request.password());

        return tokenProvider.create(member.getId());
    }

    private Member findMemberByEmail(final String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);
    }
}
