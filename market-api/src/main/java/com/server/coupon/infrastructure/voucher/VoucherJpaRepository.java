package com.server.coupon.infrastructure.voucher;

import com.server.coupon.domain.voucher.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoucherJpaRepository extends JpaRepository<Voucher, Long> {

    Optional<Voucher> findById(Long id);

    Voucher save(Voucher voucher);
}
