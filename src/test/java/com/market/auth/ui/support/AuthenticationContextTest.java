package com.market.auth.ui.support;

import com.market.auth.exception.exceptions.LoginInvalidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class AuthenticationContextTest {

    private AuthenticationContext authenticationContext;

    @BeforeEach
    void setup() {
        authenticationContext = new AuthenticationContext();
    }

    @Test
    void member_id를_반환한다() {
        // given
        authenticationContext.setAuthentication(1L);

        // when
        Long result = authenticationContext.getPrincipal();

        // then
        assertThat(result).isEqualTo(1L);
    }

    @Test
    void member_id가_없다면_예외를_발생한다() {
        // given
        authenticationContext.setAuthentication(null);

        // when & then
        assertThatThrownBy(() -> authenticationContext.getPrincipal())
                .isInstanceOf(LoginInvalidException.class);
    }

    @Test
    void 미확인_유저로_바꾼다() {
        // given
        authenticationContext.setAnonymous();

        // when
        Long result = authenticationContext.getPrincipal();

        // then
        assertThat(result).isEqualTo(-1L);
    }
}
