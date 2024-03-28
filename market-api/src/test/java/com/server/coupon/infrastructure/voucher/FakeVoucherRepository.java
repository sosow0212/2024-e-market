package com.server.coupon.infrastructure.voucher;

import com.server.coupon.domain.voucher.Voucher;
import com.server.coupon.domain.voucher.VoucherRepository;
import com.server.coupon.domain.voucher.dto.VoucherSimpleResponse;
import com.server.coupon.domain.voucher.dto.VoucherSpecificResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FakeVoucherRepository implements VoucherRepository {

    private final Map<Long, Voucher> map = new HashMap<>();

    private Long id = 0L;

    @Override
    public Voucher save(final Voucher voucher) {
        id++;

        Voucher saved = Voucher.builder()
                .id(id)
                .isUsed(voucher.getIsUsed())
                .isPublic(voucher.getIsPublic())
                .voucherNumber(voucher.getVoucherNumber())
                .description(voucher.getDescription())
                .couponId(voucher.getCouponId())
                .build();

        map.put(id, saved);
        return saved;
    }

    @Override
    public Optional<Voucher> findById(final Long voucherId) {
        return Optional.ofNullable(map.get(voucherId));
    }

    @Override
    public Page<VoucherSimpleResponse> findAllWithPaging(final Pageable pageable) {
        List<VoucherSimpleResponse> response = map.values().stream()
                .sorted(Comparator.comparing(Voucher::getId).reversed())
                .limit(10)
                .map(it -> new VoucherSimpleResponse(it.getId(), it.getCouponId(), it.getDescription(), it.getIsPublic(), it.getIsUsed(), LocalDateTime.now()))
                .toList();

        return new PageImpl<>(response);
    }

    @Override
    public Optional<VoucherSpecificResponse> findSpecificVoucherById(final Long voucherId) {
        if (!map.containsKey(voucherId)) {
            return Optional.empty();
        }

        Voucher voucher = map.get(voucherId);
        return Optional.of(
                new VoucherSpecificResponse(
                        voucher.getId(),
                        voucher.getCouponId(),
                        "couponName",
                        false,
                        true,
                        10,
                        voucher.getDescription(),
                        voucher.getVoucherNumber(),
                        voucher.getIsPublic(),
                        voucher.getIsUsed(),
                        LocalDateTime.now()
                )
        );
    }
}
