package com.server.market.domain.category;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum CategoryName {

    A000("전자제품", 0),
    A001("서적", 1),
    A002("의류", 2),
    A003("화장품", 3),
    A004("기타", 4);

    private final String name;
    private final int categoryId;

    public static CategoryName from(final Long categoryId) {
        return Arrays.stream(values())
                .filter(it -> it.categoryId == categoryId)
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
