package com.market.market.ui.support.resolver;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ProductCookieHelper {

    Cookie findCookie(final HttpServletRequest request);

    boolean hasAlreadyVisitedProduct(final Cookie cookie, final String productId);

    void updateCookie(final HttpServletResponse response, final Cookie cookie, final String productId);
}
