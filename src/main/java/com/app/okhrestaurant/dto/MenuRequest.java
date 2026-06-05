package com.app.okhrestaurant.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MenuRequest {

    private String name;

    private String description;

    private Long categoryId;

    private BigDecimal price;

    private Boolean active;
    private Boolean todaySpecial;

    public Boolean getTodaySpecial() {
        return todaySpecial;
    }

    public void setTodaySpecial(Boolean todaySpecial) {
        this.todaySpecial = todaySpecial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}