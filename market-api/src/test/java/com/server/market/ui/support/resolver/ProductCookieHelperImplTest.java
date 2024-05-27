package com.server.market.ui.support.resolver;

import com.server.market.ui.product.support.resolver.ProductCookieHelperImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
class ProductCookieHelperImplTest {

    @InjectMocks
    private ProductCookieHelperImpl productCookieHelper;

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);

    @Test
    void 상품과_관련된_쿠키가_존재하면_반환한다() {
        // given
        Cookie[] cookies = new Cookie[]{new Cookie("productView", "[1]")};
        when(request.getCookies()).thenReturn(cookies);

        // when
        Cookie result = productCookieHelper.findCookie(request);

        // then
        assertThat(result).isEqualTo(cookies[0]);
    }

    @Test
    void 상품과_관련된_쿠키가_없다면_null을_반환한다() {
        Cookie[] cookies = new Cookie[]{new Cookie("productView", "[1]")};
        when(request.getCookies()).thenReturn(null);

        // when
        Cookie result = productCookieHelper.findCookie(request);

        // then
        assertThat(result).isNull();
    }

    @Test
    void 쿠키가_존재하고_쿠키에_상품_id가_존재하면_true를_반환한다() {
        // given
        Cookie[] cookies = new Cookie[]{new Cookie("productView", "[1]")};

        // when
        boolean result = productCookieHelper.hasAlreadyVisitedProduct(cookies[0], "1");

        // then
        assertThat(result).isTrue();
    }

    @Test
    void 쿠키가_있지만_상품_id가_쿠키에_포함되지_않으면_포함시킨다() {
        // given
        Cookie[] cookies = new Cookie[]{new Cookie("productView", "[1]")};

        // when
        productCookieHelper.updateCookie(response, cookies[0], "2L");

        // then
        verify(response).addCookie(any(Cookie.class));
    }

    @Test
    void 쿠키가_없다면_response에_쿠키를_생성한다() {
        // when
        productCookieHelper.updateCookie(response, null, "1");

        // then
        verify(response).addCookie(any(Cookie.class));
    }
}
