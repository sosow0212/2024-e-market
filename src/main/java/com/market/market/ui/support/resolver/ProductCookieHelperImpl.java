package com.market.market.ui.support.resolver;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ProductCookieHelperImpl implements ProductCookieHelper {

    private static final String COOKIE_NAME = "productView";
    private static final int COOKIE_VALID_TIME = 60 * 60 * 24;

    @Override
    public Cookie findCookie(final HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            return Arrays.stream(cookies)
                    .filter(it -> it.getName().equals(COOKIE_NAME))
                    .findAny()
                    .orElseGet(null);
        }

        return null;
    }

    @Override
    public boolean hasAlreadyVisitedProduct(final Cookie cookie, final String productId) {
        return cookie != null && hasProductIdInCookie(cookie, productId);
    }

    @Override
    public void updateCookie(final HttpServletResponse response, final Cookie cookie, final String productId) {
        if (cookie != null && !hasProductIdInCookie(cookie, productId)) {
            addProductIdInCookie(cookie, productId, response);
        }

        if (cookie == null) {
            createProductIdCookie(productId, response);
        }
    }

    private boolean hasProductIdInCookie(final Cookie cookie, final String productId) {
        return cookie.getValue().contains("[" + productId + "]");
    }

    private void addProductIdInCookie(final Cookie productViewCookie, final String productId, final HttpServletResponse response) {
        productViewCookie.setValue(productViewCookie.getValue() + "_[" + productId + "]");
        addCookie(productViewCookie, response);
    }

    private void createProductIdCookie(final String productId, final HttpServletResponse response) {
        Cookie newCookie = new Cookie(COOKIE_NAME, "[" + productId + "]");
        addCookie(newCookie, response);
    }

    private void addCookie(final Cookie cookie, final HttpServletResponse response) {
        cookie.setPath("/");
        cookie.setMaxAge(COOKIE_VALID_TIME);
        response.addCookie(cookie);
    }
}
