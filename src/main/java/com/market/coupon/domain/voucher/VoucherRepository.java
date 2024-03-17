package com.market.coupon.domain.voucher;

import com.market.coupon.domain.voucher.dto.VoucherSimpleResponse;
import com.market.coupon.domain.voucher.dto.VoucherSpecificResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    Optional<Voucher> findById(Long voucherId);

    Page<VoucherSimpleResponse> findAllWithPaging(Pageable pageable);

    Optional<VoucherSpecificResponse> findSpecificVoucherById(Long voucherId);
}
