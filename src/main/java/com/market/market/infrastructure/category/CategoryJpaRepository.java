package com.market.market.infrastructure.category;

import com.market.market.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {

    Category save(final Category category);
}
