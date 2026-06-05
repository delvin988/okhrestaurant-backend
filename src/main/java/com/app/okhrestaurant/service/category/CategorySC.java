package com.app.okhrestaurant.service.category;

import com.app.okhrestaurant.entity.MenuCategory;

import java.util.List;

public interface CategorySC {

    List<MenuCategory> getAllCategories();

    MenuCategory getCategoryById(
            Long id
    );

    MenuCategory createCategory(
            MenuCategory category
    );

    MenuCategory updateCategory(
            Long id,
            MenuCategory category
    );

    MenuCategory updateStatus(
            Long id,
            Boolean active
    );

    void deleteCategory(
            Long id
    );
}