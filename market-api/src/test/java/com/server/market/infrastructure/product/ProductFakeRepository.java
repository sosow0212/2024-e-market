package com.server.market.infrastructure.product;

import com.server.market.domain.category.CategoryName;
import com.server.market.domain.product.Product;
import com.server.market.domain.product.ProductLike;
import com.server.market.domain.product.ProductRepository;
import com.server.market.domain.product.dto.ProductPagingSimpleResponse;
import com.server.market.domain.product.dto.ProductSpecificResponse;
import com.server.market.domain.product.vo.Location;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProductFakeRepository implements ProductRepository {

    private final Map<Long, ProductLike> productLikeMap = new HashMap<>();
    private final Map<Long, Product> map = new HashMap<>();
    private Long id = 0L;
    private Long productId = 0L;

    @Override
    public Product save(final Product product) {
        id++;

        Product saved = Product.builder()
                .id(id)
                .description(product.getDescription())
                .statisticCount(product.getStatisticCount())
                .memberId(product.getMemberId())
                .categoryId(product.getCategoryId())
                .price(product.getPrice())
                .productStatus(product.getProductStatus())
                .build();

        map.put(id, saved);
        return saved;
    }

    @Override
    public Optional<Product> findById(final Long productId) {
        return map.keySet().stream()
                .filter(it -> it.equals(productId))
                .map(map::get)
                .findAny();
    }

    @Override
    public Optional<Product> findByIdWithPessimisticLock(final Long productId) {
        return map.keySet().stream()
                .filter(it -> it.equals(productId))
                .map(map::get)
                .findAny();
    }

    @Override
    public Optional<ProductSpecificResponse> findSpecificProductById(final Long productId, final Long memberId) {
        if (map.containsKey(id)) {
            Product product = map.get(id);
            return Optional.of(new ProductSpecificResponse(product.getId(), product.getDescription().getLocation(), product.getDescription().getTitle(), product.getDescription().getContent(), product.getPrice().getPrice(), product.getProductStatus(), product.getStatisticCount().getVisitedCount(), product.getStatisticCount().getContactCount(), product.getCategoryId(), CategoryName.A000, "owner", true, 1, true, LocalDateTime.now()));
        }

        return Optional.empty();
    }

    @Override
    public void deleteProductById(final Long productId) {
        map.remove(productId);
    }

    @Override
    public List<ProductPagingSimpleResponse> findAllProductsInCategoryWithPaging(final Long memberId, final Long productId, final Long categoryId, final int pageSize) {
        if (productId == null) {
            return map.values().stream()
                    .sorted(Comparator.comparing(Product::getId).reversed())
                    .limit(pageSize)
                    .map(ProductFakeRepository::parse)
                    .toList();
        }

        return map.values().stream()
                .filter(it -> it.getId() < productId)
                .filter(it -> it.getCategoryId().equals(categoryId))
                .sorted(Comparator.comparing(Product::getId).reversed())
                .limit(pageSize)
                .map(ProductFakeRepository::parse)
                .toList();
    }

    @Override
    public boolean existsByProductIdAndMemberId(final Long productId, final Long memberId) {
        return false;
    }

    @Override
    public void deleteByProductIdAndMemberId(final Long productId, final Long memberId) {
    }

    @Override
    public ProductLike saveProductLike(final ProductLike productLike) {
        productId++;
        ProductLike savedProductLike = ProductLike.builder()
                .id(productId)
                .build();

        this.productLikeMap.put(productId, savedProductLike);
        return savedProductLike;
    }

    @Override
    public List<ProductPagingSimpleResponse> findLikesProducts(final Long memberId) {
        return List.of();
    }

    private static ProductPagingSimpleResponse parse(final Product product) {
        return new ProductPagingSimpleResponse(
                product.getId(),
                Location.BUILDING_CENTER,
                product.getDescription().getTitle(),
                product.getPrice().getPrice(),
                product.getStatisticCount().getVisitedCount(),
                product.getStatisticCount().getContactCount(),
                product.getProductStatus(),
                "owner",
                10,
                true,
                LocalDateTime.now()
        );
    }
}
