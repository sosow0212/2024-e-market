package com.server.market.application.product;

import com.server.global.event.Events;
import com.server.market.application.product.dto.ProductCreateRequest;
import com.server.market.application.product.dto.ProductUpdateRequest;
import com.server.market.application.product.dto.ProductUpdateResult;
import com.server.market.application.product.dto.UsingCouponRequest;
import com.server.market.domain.product.Product;
import com.server.market.domain.product.ProductImageConverter;
import com.server.market.domain.product.ProductLike;
import com.server.market.domain.product.ProductRepository;
import com.server.market.domain.product.event.CouponExistValidatedEvent;
import com.server.market.domain.product.event.ProductSoldEvent;
import com.server.market.domain.product.event.UsedCouponDeletedEvent;
import com.server.market.exception.exceptions.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImageConverter productImageConverter;
    private final ProductImageUploader productImageUploader;

    public Long uploadProduct(final Long memberId, final Long categoryId, final ProductCreateRequest request) {
        Product product = Product.of(request.title(), request.content(), request.location(), request.price(), categoryId, memberId, request.images(), productImageConverter);
        Product savedProduct = productRepository.save(product);
        productImageUploader.upload(product.getProductImages(), request.images());
        return savedProduct.getId();
    }

    public Product addViewCount(final Long productId, final Boolean canAddViewCount) {
        Product product = findBoardWithPessimisticLock(productId);
        product.view(canAddViewCount);
        return product;
    }

    public void update(final Long productId, final Long memberId, final ProductUpdateRequest request) {
        Product product = findProduct(productId);
        ProductUpdateResult result = product.updateDescription(request.title(), request.content(), request.location(), request.price(), request.categoryId(), memberId, request.addedImages(), request.deletedImages(), productImageConverter);

        productImageUploader.upload(result.addedImages(), request.addedImages());
        productImageUploader.delete(result.deletedImages());
    }

    public void delete(final Long productId, final Long memberId) {
        Product product = findProduct(productId);
        product.validateOwner(memberId);

        productRepository.deleteProductById(productId);
    }

    public void buyProducts(final Long productId, final Long buyerId, final UsingCouponRequest request) {
        Events.raise(new CouponExistValidatedEvent(buyerId, request.usingCouponIds()));

        Product product = findProduct(productId);
        product.sell();

        Events.raise(new UsedCouponDeletedEvent(buyerId, request.usingCouponIds()));
        Events.raise(new ProductSoldEvent(buyerId, product.getMemberId(), productId, request.productOriginalPrice(), request.productDiscountPrice(), request.usingCouponIds()));
    }

    private Product findProduct(final Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
    }

    private Product findBoardWithPessimisticLock(final Long productId) {
        return productRepository.findByIdWithPessimisticLock(productId)
                .orElseThrow(ProductNotFoundException::new);
    }

    public boolean likes(final Long productId, final Long memberId) {
        Product product = findBoardWithPessimisticLock(productId);
        boolean isNeedToIncrease = isNeedToIncreaseLikeCount(productId, memberId);
        product.likes(isNeedToIncrease);
        return isNeedToIncrease;
    }

    private boolean isNeedToIncreaseLikeCount(final Long productId, final Long memberId) {
        if (productRepository.existsByProductIdAndMemberId(productId, memberId)) {
            productRepository.deleteByProductIdAndMemberId(productId, memberId);
            return false;
        }

        productRepository.saveProductLike(new ProductLike(productId, memberId));
        return true;
    }
}
