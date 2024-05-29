package com.server.market.domain.product.vo;

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
    private static final int DEFAULT_LIKED_COUNT = 0;

    @Column(nullable = false)
    private Integer visitedCount;

    @Column(nullable = false)
    private Integer contactCount;

    @Column(nullable = false)
    private Integer likedCount;

    public static StatisticCount createDefault() {
        return new StatisticCount(DEFAULT_VISITED_COUNT, DEFAULT_CONTACT_COUNT, DEFAULT_LIKED_COUNT);
    }

    public void view(final boolean canAddViewCount) {
        if (canAddViewCount) {
            this.visitedCount++;
        }
    }

    public void contact() {
        this.contactCount++;
    }

    public void liked() {
        this.likedCount++;
    }

    public void unlike() {
        this.likedCount--;
    }
}
