package com.server.member.domain.member;

import com.server.global.domain.BaseEntity;
import com.server.member.exception.exceptions.member.MemberAuthInvalidException;
import com.server.member.exception.exceptions.member.PasswordNotMatchedException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@EqualsAndHashCode(of = "id", callSuper = false)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private MemberRole memberRole;

    public boolean isAdmin() {
        return this.memberRole.isAdministrator();
    }

    public static Member createDefaultRole(final String email,
                                           final String password,
                                           final NicknameGenerator nicknameGenerator) {
        return Member.builder()
                .email(email)
                .password(password)
                .nickname(nicknameGenerator.createRandomNickname())
                .memberRole(MemberRole.MEMBER)
                .build();
    }

    public void validatePassword(final String password) {
        if (!this.password.equals(password)) {
            throw new PasswordNotMatchedException();
        }
    }

    public void validateAuth(final Long memberId) {
        if (!this.id.equals(memberId)) {
            throw new MemberAuthInvalidException();
        }
    }
}
