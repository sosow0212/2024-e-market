package com.server.market.infrastructure.category;

import com.server.market.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {

    Category save(final Category category);
}
