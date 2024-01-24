package com.market.market.domain.product;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class StatisticCount {

    private static final int DEFAULT_VISITED_COUNT = 0;
    private static final int DEFAULT_CONTACT_COUNT = 0;

    @Column(nullable = false)
    private Integer visitedCount;

    @Column(nullable = false)
    private Integer contactCount;

    public static StatisticCount createDefault() {
        return new StatisticCount(DEFAULT_VISITED_COUNT, DEFAULT_CONTACT_COUNT);
    }

    public void view(final boolean isNeedToBeAddViewCount) {
        if (isNeedToBeAddViewCount) {
            this.visitedCount++;
        }
    }

    public void contact() {
        contactCount++;
    }
}
