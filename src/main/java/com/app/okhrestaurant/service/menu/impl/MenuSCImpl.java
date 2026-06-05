package com.app.okhrestaurant.service.menu.impl;

import com.app.okhrestaurant.dto.MenuRequest;
import com.app.okhrestaurant.entity.Menu;
import com.app.okhrestaurant.entity.MenuCategory;
import com.app.okhrestaurant.repository.MenuCategoryRepository;
import com.app.okhrestaurant.repository.MenuRepository;
import com.app.okhrestaurant.service.menu.MenuSC;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MenuSCImpl
        implements MenuSC {

    private final MenuRepository menuRepository;

    private final MenuCategoryRepository categoryRepository;

    public MenuSCImpl(
            MenuRepository menuRepository,
            MenuCategoryRepository categoryRepository
    ) {

        this.menuRepository =
                menuRepository;

        this.categoryRepository =
                categoryRepository;
    }

    @Override
    public List<Menu> getAllMenus() {

        return menuRepository.findAll();
    }

    @Override
    public Menu getMenuById(Long id) {

        return menuRepository
                .findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Menu not found"
                        )
                );
    }

    @Override
    public Menu createMenu(MenuRequest req) {
        MenuCategory category = categoryRepository.findById(req.getCategoryId())
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Category not found"
                                )
                        );
        Menu menu = new Menu();
        menu.setName(req.getName());
        menu.setDescription(req.getDescription());
        menu.setCategory(category);
        menu.setPrice(req.getPrice());
        menu.setActive(req.getActive());
        menu.setUpdatedAt(LocalDateTime.now());
        menu.setCreatedAt(LocalDateTime.now());
        menu.setTodaySpecial(req.getTodaySpecial());
        return menuRepository.save(menu);
    }

    @Override
    public Menu updateMenu(Long id, MenuRequest req) {

        Menu menu = getMenuById(id);

        MenuCategory category = categoryRepository.findById(req.getCategoryId())
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Category not found"
                                )
                        );
        menu.setName(req.getName());
        menu.setDescription(req.getDescription());
        menu.setCategory(category);
        menu.setPrice(req.getPrice());
        menu.setUpdatedAt(LocalDateTime.now());
        menu.setTodaySpecial(req.getTodaySpecial());
        return menuRepository.save(menu);
    }

    @Override
    public Menu updateStatus(
            Long id,
            Boolean active
    ) {

        Menu menu =
                getMenuById(id);

        menu.setActive(
                active
        );
        menu.setUpdatedAt(
                LocalDateTime.now()
        );
        return menuRepository.save(
                menu
        );
    }

    @Override
    public void deleteMenu(
            Long id
    ) {

        menuRepository.deleteById(
                id
        );
    }

    @Override
    public Menu updateTodaySpecial(
            Long id,
            Boolean todaySpecial
    ) {

        Menu menu =
                getMenuById(id);

        menu.setTodaySpecial(
                todaySpecial
        );

        menu.setUpdatedAt(
                LocalDateTime.now()
        );

        return menuRepository.save(
                menu
        );
    }
}