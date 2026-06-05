package com.app.okhrestaurant.repository;

import com.app.okhrestaurant.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository
        extends JpaRepository<Menu, Long> {
}