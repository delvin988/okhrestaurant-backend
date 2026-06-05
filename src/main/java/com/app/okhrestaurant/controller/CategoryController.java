package com.app.okhrestaurant.controller;

import com.app.okhrestaurant.entity.MenuCategory;
import com.app.okhrestaurant.service.category.CategorySC;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/menu-categories")
public class CategoryController {

    private final CategorySC categorySC;

    public CategoryController(
            CategorySC categorySC
    ) {
        this.categorySC =
                categorySC;
    }

    @GetMapping
    public List<MenuCategory>
    getAllCategories() {

        return categorySC
                .getAllCategories();
    }

    @GetMapping("/{id}")
    public MenuCategory getCategoryById(
            @PathVariable Long id
    ) {

        return categorySC
                .getCategoryById(id);
    }

    @PostMapping
    public MenuCategory createCategory(
            @RequestBody MenuCategory category
    ) {

        return categorySC
                .createCategory(category);
    }

    @PutMapping("/{id}")
    public MenuCategory updateCategory(
            @PathVariable Long id,
            @RequestBody MenuCategory category
    ) {

        return categorySC
                .updateCategory(
                        id,
                        category
                );
    }

    @PutMapping("/{id}/status")
    public MenuCategory updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, Boolean> req
    ) {

        return categorySC
                .updateStatus(
                        id,
                        req.get("active")
                );
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(
            @PathVariable Long id
    ) {

        categorySC.deleteCategory(id);
    }
}