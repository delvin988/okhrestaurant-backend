package com.app.okhrestaurant.repository;

import com.app.okhrestaurant.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRP extends JpaRepository<Otp, Long> {

    Optional<Otp> findTopByPhoneOrderByIdDesc(String phone);
}
