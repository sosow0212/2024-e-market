package com.server.market.domain.product.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
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

    @Lob
    @Column(nullable = false)
    private String content;

    // todo : images

    public Description(final String title, final String content) {
        this.title = title;
        this.content = content;
    }

    public void update(final String title, final String content) {
        this.title = title;
        this.content = content;
    }
}
