package com.app.okhrestaurant.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;

    private String phone;

    private String email;

    private LocalDateTime bookingTime;

    private Integer numberOfPeople;

    @Column(columnDefinition = "TEXT")
    private String specialRequest;

    private Boolean wantMenu;

    @Column(columnDefinition = "TEXT")
    private String selectedMenus;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // =========================
    // GETTER SETTER
    // =========================

    public Long getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public String getSpecialRequest() {
        return specialRequest;
    }

    public void setSpecialRequest(String specialRequest) {
        this.specialRequest = specialRequest;
    }

    public Boolean getWantMenu() {
        return wantMenu;
    }

    public void setWantMenu(Boolean wantMenu) {
        this.wantMenu = wantMenu;
    }

    public String getSelectedMenus() {
        return selectedMenus;
    }

    public void setSelectedMenus(String selectedMenus) {
        this.selectedMenus = selectedMenus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}