package com.server.market.ui;

import com.server.market.application.ProductQueryService;
import com.server.market.application.ProductService;
import com.server.market.application.dto.ProductCreateRequest;
import com.server.market.application.dto.ProductUpdateRequest;
import com.server.market.application.dto.UsingCouponRequest;
import com.server.market.domain.product.dto.ProductPagingSimpleResponse;
import com.server.market.domain.product.dto.ProductSpecificResponse;
import com.server.market.ui.support.ViewCountChecker;
import com.server.member.ui.auth.support.AuthMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/categories")
@RestController
public class ProductController {

    private final ProductService productService;
    private final ProductQueryService productQueryService;

    @GetMapping("/{categoryId}/products")
    public ResponseEntity<List<ProductPagingSimpleResponse>> findAllProductsInCategory(@PathVariable("categoryId") final Long categoryId,
                                                                                       @RequestParam(name = "productId", required = false) final Long productId,
                                                                                       @RequestParam(name = "pageSize") final Integer pageSize) {
        return ResponseEntity.ok(productQueryService.findAllProductsInCategory(productId, categoryId, pageSize));
    }

    @PostMapping("/{categoryId}/products")
    public ResponseEntity<Long> uploadProduct(@PathVariable("categoryId") final Long categoryId,
                                              @AuthMember final Long memberId,
                                              @Valid @RequestBody final ProductCreateRequest request) {
        Long savedProductId = productService.uploadProduct(memberId, categoryId, request);
        return ResponseEntity.created(URI.create("/api/categories/" + categoryId + "/products/" + savedProductId))
                .build();
    }

    @GetMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<ProductSpecificResponse> findProductById(@PathVariable("productId") final Long productId,
                                                                   @PathVariable("categoryId") final Long categoryId,
                                                                   @ViewCountChecker final Boolean canAddViewCount) {
        productService.addViewCount(productId, canAddViewCount);
        return ResponseEntity.ok(productQueryService.findById(productId));
    }

    @PatchMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<Void> updateProduct(@PathVariable("productId") final Long productId,
                                              @PathVariable("categoryId") final Long categoryId,
                                              @AuthMember final Long memberId,
                                              @Valid @RequestBody final ProductUpdateRequest request) {
        productService.update(productId, memberId, request);
        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<Long> deleteProduct(@PathVariable("productId") final Long productId,
                                              @PathVariable("categoryId") final Long categoryId,
                                              @AuthMember final Long memberId) {
        productService.delete(productId, memberId);
        return ResponseEntity.noContent()
                .build();
    }

    @PostMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<Void> buyProducts(@PathVariable("productId") final Long productId,
                                            @PathVariable("categoryId") final Long categoryId,
                                            @AuthMember final Long memberId,
                                            @RequestBody final UsingCouponRequest usingCouponRequest) {
        productService.buyProducts(productId, memberId, usingCouponRequest);
        return ResponseEntity.ok().build();
    }
}
