package com.server.member.domain.auth;

public interface TokenProvider {

    String create(final Long id);

    Long extract(final String token);
}
