package com.app.okhrestaurant.service.menu;

import com.app.okhrestaurant.dto.MenuRequest;
import com.app.okhrestaurant.entity.Menu;

import java.util.List;

public interface MenuSC {

    List<Menu> getAllMenus();

    Menu getMenuById(Long id);

    Menu createMenu(MenuRequest req);

    Menu updateMenu(
            Long id,
            MenuRequest req
    );

    Menu updateStatus(
            Long id,
            Boolean active
    );

    void deleteMenu(Long id);
    Menu updateTodaySpecial(
            Long id,
            Boolean todaySpecial
    );
}