package com.server.coupon.ui.voucher;

import com.server.coupon.application.voucher.VoucherQueryService;
import com.server.coupon.application.voucher.VoucherService;
import com.server.coupon.application.voucher.dto.VoucherCreateRequest;
import com.server.coupon.application.voucher.dto.VoucherNumberRequest;
import com.server.coupon.domain.voucher.Voucher;
import com.server.coupon.domain.voucher.dto.VoucherPageResponse;
import com.server.coupon.domain.voucher.dto.VoucherSimpleResponse;
import com.server.coupon.domain.voucher.dto.VoucherSpecificResponse;
import com.server.member.ui.auth.support.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class VoucherController {

    private final VoucherService voucherService;
    private final VoucherQueryService voucherQueryService;

    @PostMapping("/vouchers")
    public ResponseEntity<Long> createPrivateVoucher(@RequestBody final VoucherCreateRequest request) {
        Voucher voucher = voucherService.savePrivateVoucher(request);
        return ResponseEntity.created(URI.create("/api/vouchers/" + voucher.getId()))
                .build();
    }

    @PostMapping("/vouchers/{voucherId}")
    public ResponseEntity<Void> useVoucher(
            @AuthMember final Long memberId,
            @PathVariable final Long voucherId,
            @RequestBody final VoucherNumberRequest request
    ) {
        voucherService.useVoucher(voucherId, request, memberId);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/vouchers")
    public ResponseEntity<VoucherPageResponse> findVoucherWithPaging(@PageableDefault(sort = "id", direction = DESC) final Pageable pageable) {
        Page<VoucherSimpleResponse> result = voucherQueryService.findAllWithPaging(pageable);
        return ResponseEntity.ok(VoucherPageResponse.of(result, pageable));
    }

    @GetMapping("/vouchers/{voucherId}")
    public ResponseEntity<VoucherSpecificResponse> findSpecificVoucherById(@PathVariable final Long voucherId) {
        return ResponseEntity.ok(voucherQueryService.findSpecificVoucher(voucherId));
    }
}
