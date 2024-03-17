package com.market.coupon.infrastructure.voucher;

import com.market.coupon.domain.voucher.Voucher;
import com.market.coupon.domain.voucher.VoucherRepository;
import com.market.coupon.domain.voucher.dto.VoucherSimpleResponse;
import com.market.coupon.domain.voucher.dto.VoucherSpecificResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class VoucherRepositoryImpl implements VoucherRepository {

    private final VoucherJpaRepository voucherJpaRepository;
    private final VoucherQueryRepository voucherQueryRepository;


    @Override
    public Voucher save(final Voucher voucher) {
        return voucherJpaRepository.save(voucher);
    }

    @Override
    public Optional<Voucher> findById(final Long voucherId) {
        return voucherJpaRepository.findById(voucherId);
    }

    @Override
    public Page<VoucherSimpleResponse> findAllWithPaging(final Pageable pageable) {
        return voucherQueryRepository.findAllVouchers(pageable);
    }

    @Override
    public Optional<VoucherSpecificResponse> findSpecificVoucherById(final Long voucherId) {
        return voucherQueryRepository.findById(voucherId);
    }


}
