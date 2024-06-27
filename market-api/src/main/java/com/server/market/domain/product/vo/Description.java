package com.server.market.domain.product.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Description {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Location location;

    @Lob
    @Column(nullable = false)
    private String content;

    public Description(
            final String title,
            final String content,
            final Location location
    ) {
        this.title = title;
        this.content = content;
        this.location = location;
    }

    public void update(
            final String title,
            final String content,
            final Location location
    ) {
        this.title = title;
        this.content = content;
        this.location = location;
    }
}
