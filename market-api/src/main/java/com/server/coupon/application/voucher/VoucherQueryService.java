package com.server.coupon.application.voucher;

import com.server.coupon.domain.voucher.VoucherRepository;
import com.server.coupon.domain.voucher.dto.VoucherSimpleResponse;
import com.server.coupon.domain.voucher.dto.VoucherSpecificResponse;
import com.server.coupon.exception.exceptions.VoucherNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class VoucherQueryService {

    private final VoucherRepository voucherRepository;

    public Page<VoucherSimpleResponse> findAllWithPaging(final Pageable pageable) {
        return voucherRepository.findAllWithPaging(pageable);
    }

    public VoucherSpecificResponse findSpecificVoucher(final Long voucherId) {
        return voucherRepository.findSpecificVoucherById(voucherId)
                .orElseThrow(VoucherNotFoundException::new);
    }
}
