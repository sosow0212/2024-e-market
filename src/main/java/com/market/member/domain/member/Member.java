package com.market.member.domain.member;

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
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

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
                                           final String nickname) {
        return Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .memberRole(MemberRole.MEMBER)
                .build();
    }

    public boolean hasSamePassword(final String password) {
        return this.password.equals(password);
    }
}
