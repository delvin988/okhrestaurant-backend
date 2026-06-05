package com.app.okhrestaurant.controller;

import com.app.okhrestaurant.dto.MenuRequest;
import com.app.okhrestaurant.entity.Menu;
import com.app.okhrestaurant.service.menu.MenuSC;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/menus")
public class MenuController {

    private final MenuSC menuSC;

    public MenuController(
            MenuSC menuSC
    ) {
        this.menuSC = menuSC;
    }

    @GetMapping
    public List<Menu> getAllMenus() {

        return menuSC.getAllMenus();
    }

    @GetMapping("/{id}")
    public Menu getMenuById(
            @PathVariable Long id
    ) {

        return menuSC.getMenuById(id);
    }

    @PostMapping
    public Menu createMenu(
            @RequestBody MenuRequest req
    ) {

        return menuSC.createMenu(req);
    }

    @PutMapping("/{id}")
    public Menu updateMenu(
            @PathVariable Long id,
            @RequestBody MenuRequest req
    ) {

        return menuSC.updateMenu(
                id,
                req
        );
    }

    @PutMapping("/{id}/status")
    public Menu updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, Boolean> req
    ) {

        return menuSC.updateStatus(
                id,
                req.get("active")
        );
    }

    @DeleteMapping("/{id}")
    public void deleteMenu(
            @PathVariable Long id
    ) {

        menuSC.deleteMenu(id);
    }
    @PutMapping("/{id}/today-special")
    public Menu updateTodaySpecial(
            @PathVariable Long id,
            @RequestBody Map<String, Boolean> req
    ) {

        return menuSC.updateTodaySpecial(
                id,
                req.get("todaySpecial")
        );
    }
}