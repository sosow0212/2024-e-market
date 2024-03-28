package com.server.community.domain.board.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Post {

    @Column(length = 32, nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    public static Post of(final String title, final String content) {
        return new Post(title, content);
    }

    public void update(final String title, final String content) {
        this.title = title;
        this.content = content;
    }
}
