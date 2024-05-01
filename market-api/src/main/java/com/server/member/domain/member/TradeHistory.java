package com.server.member.domain.member;

import com.server.global.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.joining;

@Getter
@Builder
@EqualsAndHashCode(of = "id", callSuper = false)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "trade_history")
public class TradeHistory extends BaseEntity {

    private static final String JOINING_DELIMITER = ",";
    private static final String EMPTY_VALUE = "";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long buyerId;

    @Column(nullable = false)
    private Long sellerId;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private int productOriginPrice;

    @Column(nullable = false)
    private int productDiscountPrice;

    @Column(nullable = false)
    private String usingCouponIds;

    public TradeHistory(final Long buyerId, final Long sellerId, final Long productId, final int productOriginPrice, final int productDiscountPrice, final List<Long> usingCouponIds) {
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.productId = productId;
        this.productOriginPrice = productOriginPrice;
        this.productDiscountPrice = productDiscountPrice;
        this.usingCouponIds = getUsingCouponIds(usingCouponIds);
    }

    private String getUsingCouponIds(final List<Long> usingCouponIds) {
        if (usingCouponIds.isEmpty()) {
            return EMPTY_VALUE;
        }

        return usingCouponIds.stream()
                .map(String::valueOf)
                .collect(joining(JOINING_DELIMITER));
    }
}
