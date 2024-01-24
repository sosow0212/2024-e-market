package com.market.market.ui;

import com.market.market.application.ProductService;
import com.market.market.application.dto.ProductCreateRequest;
import com.market.market.application.dto.ProductUpdateRequest;
import com.market.market.domain.product.Product;
import com.market.market.ui.dto.ProductResponse;
import com.market.market.ui.dto.ProductsResponse;
import com.market.market.ui.support.ViewCountChecker;
import com.market.member.ui.auth.support.AuthMember;
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
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/categories")
@RestController
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{categoryId}/products")
    public ResponseEntity<ProductsResponse> findAllProductsInCategory(@PathVariable("categoryId") final Long categoryId) {
        // TODO : 페이징
        List<Product> products = productService.findAllProductsInCategory(categoryId);
        return ResponseEntity.ok(ProductsResponse.from(products));
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
    public ResponseEntity<ProductResponse> findProductById(@PathVariable("productId") final Long productId,
                                                           @PathVariable("categoryId") final Long categoryId,
                                                           @ViewCountChecker final Boolean isNeedToBeAddViewCount) {
        Product product = productService.findProductById(productId, isNeedToBeAddViewCount);
        return ResponseEntity.ok(ProductResponse.from(product));
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
}
