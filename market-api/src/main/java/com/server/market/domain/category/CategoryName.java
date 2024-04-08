package com.server.market.domain.category;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CategoryName {

    A000("default"),
    A001("electronic"),
    A002("fashion");

    private final String name;
}
