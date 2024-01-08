package com.market.auth.domain;

public interface TokenProvider {

    String create(final Long id);

    Long extract(final String token);
}
