package com.app.okhrestaurant.service.category.impl;

import com.app.okhrestaurant.entity.MenuCategory;
import com.app.okhrestaurant.repository.MenuCategoryRepository;
import com.app.okhrestaurant.service.category.CategorySC;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorySCImpl
        implements CategorySC {

    private final MenuCategoryRepository
            categoryRepository;

    public CategorySCImpl(
            MenuCategoryRepository categoryRepository
    ) {

        this.categoryRepository =
                categoryRepository;
    }

    @Override
    public List<MenuCategory> getAllCategories() {

        return categoryRepository.findAll();
    }

    @Override
    public MenuCategory getCategoryById(
            Long id
    ) {

        return categoryRepository
                .findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Category not found"
                        )
                );
    }

    @Override
    public MenuCategory createCategory(
            MenuCategory category
    ) {

        boolean exists =
                categoryRepository
                        .existsByName(
                                category.getName()
                        );

        if (exists) {

            throw new RuntimeException(
                    "Category already exists"
            );
        }

        return categoryRepository.save(
                category
        );
    }

    @Override
    public MenuCategory updateCategory(
            Long id,
            MenuCategory req
    ) {

        MenuCategory category =
                getCategoryById(id);

        category.setName(
                req.getName()
        );

        return categoryRepository.save(
                category
        );
    }

    @Override
    public MenuCategory updateStatus(
            Long id,
            Boolean active
    ) {

        MenuCategory category =
                getCategoryById(id);

        category.setActive(
                active
        );

        return categoryRepository.save(
                category
        );
    }

    @Override
    public void deleteCategory(
            Long id
    ) {

        categoryRepository.deleteById(
                id
        );
    }
}