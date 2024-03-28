package com.server.market.infrastructure.category;

import com.server.market.domain.category.Category;
import com.server.market.domain.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public Category save(final Category category) {
        return categoryJpaRepository.save(category);
    }
}
