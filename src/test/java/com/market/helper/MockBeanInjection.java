package com.market.helper;

import com.market.board.application.BoardService;
import com.market.member.application.auth.AuthService;
import com.market.member.domain.auth.TokenProvider;
import com.market.member.ui.auth.support.AuthenticationContext;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;

@MockBean(JpaMetamodelMappingContext.class)
public class MockBeanInjection {

    @MockBean
    protected TokenProvider tokenProvider;

    @MockBean
    protected AuthenticationContext authenticationContext;

    @MockBean
    protected AuthService authService;

    @MockBean
    protected BoardService boardService;
}
