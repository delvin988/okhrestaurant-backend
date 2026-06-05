package com.app.okhrestaurant.repository;

import com.app.okhrestaurant.entity.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuCategoryRepository
        extends JpaRepository<
        MenuCategory,
        Long
        > {
    boolean existsByName(
            String name
    );
}