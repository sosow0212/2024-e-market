package com.market.coupon.application.voucher;

import com.market.coupon.application.voucher.dto.VoucherCreateRequest;
import com.market.coupon.application.voucher.dto.VoucherNumberRequest;
import com.market.coupon.domain.voucher.Voucher;
import com.market.coupon.domain.voucher.VoucherNumberGenerator;
import com.market.coupon.domain.voucher.VoucherRepository;
import com.market.coupon.domain.voucher.event.UsedVoucherEvent;
import com.market.coupon.domain.voucher.event.ValidatedExistedCouponEvent;
import com.market.coupon.exception.exceptions.VoucherNotFoundException;
import com.market.global.event.Events;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherNumberGenerator voucherNumberGenerator;

    public Voucher savePrivateVoucher(final VoucherCreateRequest request) {
        Events.raise(new ValidatedExistedCouponEvent(request.couponId()));
        Voucher voucher = Voucher.createPrivate(request.couponId(), request.description(), voucherNumberGenerator);
        return voucherRepository.save(voucher);
    }

    public void useVoucher(final Long voucherId, final VoucherNumberRequest request, final Long memberId) {
        Voucher voucher = findVoucher(voucherId);
        voucher.use(request.voucherNumber());
        Events.raise(new UsedVoucherEvent(voucher.getCouponId(), memberId));
    }

    private Voucher findVoucher(final Long id) {
        return voucherRepository.findById(id)
                .orElseThrow(VoucherNotFoundException::new);
    }
}
