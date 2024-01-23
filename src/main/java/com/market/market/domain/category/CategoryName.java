package com.market.market.domain.category;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CategoryName {

    A001("electorinic"),
    A002("fashion");

    private final String name;
}
