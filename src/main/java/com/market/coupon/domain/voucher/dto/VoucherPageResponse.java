package com.market.coupon.domain.voucher.dto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public record VoucherPageResponse(
        List<VoucherSimpleResponse> vouchers,
        int nextPage
) {

    private static final int NEXT_PAGE_INDEX = 1;
    private static final int NO_MORE_PAGE = -1;

    public static VoucherPageResponse of(final Page<VoucherSimpleResponse> vouchers, final Pageable pageable) {
        return new VoucherPageResponse(vouchers.getContent(), getNextPage(pageable.getPageNumber(), vouchers));
    }

    private static int getNextPage(final int pageNumber, final Page<VoucherSimpleResponse> vouchers) {
        if (vouchers.hasNext()) {
            return pageNumber + NEXT_PAGE_INDEX;
        }

        return NO_MORE_PAGE;
    }
}
