package com.server.market.ui.product;

import com.server.market.application.product.ProductQueryService;
import com.server.market.application.product.ProductService;
import com.server.market.application.product.dto.ProductCreateRequest;
import com.server.market.application.product.dto.ProductUpdateRequest;
import com.server.market.application.product.dto.ProductWithImageResponse;
import com.server.market.application.product.dto.UsingCouponRequest;
import com.server.market.domain.product.dto.ProductPagingSimpleResponse;
import com.server.market.ui.product.support.ViewCountChecker;
import com.server.member.ui.auth.support.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public ResponseEntity<List<ProductPagingSimpleResponse>> findAllProductsInCategory(
            @AuthMember final Long memberId,
            @PathVariable("categoryId") final Long categoryId,
            @RequestParam(name = "productId", required = false) final Long productId,
            @RequestParam(name = "pageSize") final Integer pageSize) {
        return ResponseEntity.ok(productQueryService.findAllProductsInCategory(memberId, productId, categoryId, pageSize));
    }

    @PostMapping("/{categoryId}/products")
    public ResponseEntity<Long> uploadProduct(
            @PathVariable("categoryId") final Long categoryId,
            @AuthMember final Long memberId,
            @ModelAttribute final ProductCreateRequest request
    ) {
        Long savedProductId = productService.uploadProduct(memberId, categoryId, request);
        return ResponseEntity.created(URI.create("/api/categories/" + categoryId + "/products/" + savedProductId))
                .build();
    }

    @GetMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<ProductWithImageResponse> findProductById(
            @PathVariable("productId") final Long productId,
            @PathVariable("categoryId") final Long categoryId,
            @AuthMember final Long memberId,
            @ViewCountChecker final Boolean canAddViewCount
    ) {
        productService.addViewCount(productId, canAddViewCount);
        return ResponseEntity.ok(productQueryService.findById(productId, memberId));
    }

    @PatchMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<Void> updateProduct(
            @PathVariable("productId") final Long productId,
            @PathVariable("categoryId") final Long categoryId,
            @AuthMember final Long memberId,
            @ModelAttribute final ProductUpdateRequest request
    ) {
        productService.update(productId, memberId, request);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/{categoryId}/products/likes")
    public ResponseEntity<List<ProductPagingSimpleResponse>> findLikesProduct(
            @PathVariable("categoryId") final Long categoryId,
            @AuthMember final Long memberId
    ) {
        return ResponseEntity.ok(productQueryService.findLikesProducts(memberId));
    }

    @PatchMapping("/{categoryId}/products/{productId}/likes")
    public ResponseEntity<Boolean> likesProduct(
            @PathVariable("categoryId") final Long categoryId,
            @PathVariable("productId") final Long productId,
            @AuthMember final Long memberId
    ) {
        System.out.println("gogo " + memberId + " " + productId);
        boolean likes = productService.likes(productId, memberId);
        return ResponseEntity.ok(likes);
    }

    @DeleteMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<Long> deleteProduct(
            @PathVariable("productId") final Long productId,
            @PathVariable("categoryId") final Long categoryId,
            @AuthMember final Long memberId
    ) {
        productService.delete(productId, memberId);
        return ResponseEntity.noContent()
                .build();
    }

    @PostMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<Void> buyProducts(
            @PathVariable("productId") final Long productId,
            @PathVariable("categoryId") final Long categoryId,
            @AuthMember final Long memberId,
            @RequestBody final UsingCouponRequest usingCouponRequest
    ) {
        productService.buyProducts(productId, memberId, usingCouponRequest);
        return ResponseEntity.ok().build();
    }
}
