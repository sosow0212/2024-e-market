package com.server.market.domain.product.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Location {

    BUILDING_THREE("3공학관"),
    BUILDING_FIVE("5공학관"),
    BUILDING_LIBRARY("명진당"),
    BUILDING_CENTER("학생회관"),
    NEAR_MJU("명지대 주변 (협의)");

    private final String content;
}
