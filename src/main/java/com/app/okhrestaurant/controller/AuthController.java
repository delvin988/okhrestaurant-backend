package com.app.okhrestaurant.controller;

import com.app.okhrestaurant.dto.LoginRequest;
import com.app.okhrestaurant.security.JwtUtil;
import com.app.okhrestaurant.service.otp.OtpSC;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.app.okhrestaurant.entity.AdminUser;
import com.app.okhrestaurant.repository.AdminUserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AdminUserRepository adminUserRepository;

    private final PasswordEncoder passwordEncoder;
    private final OtpSC otpSC;

    public AuthController(AdminUserRepository adminUserRepository, PasswordEncoder passwordEncoder) {
        this.adminUserRepository = adminUserRepository;
        this.passwordEncoder = passwordEncoder;
        otpSC = null;
    }

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(
            @RequestBody Map<String, String> req) {

        try {

            otpSC.sendOtp(
                    req.get("phone")
            );

            return ResponseEntity.ok(
                    Map.of(
                            "message",
                            "OTP sent"
                    )
            );

        } catch (RuntimeException e) {

            return ResponseEntity.badRequest()
                    .body(
                            Map.of(
                                    "message",
                                    e.getMessage()
                            )
                    );
        }
    }

    @PostMapping("/verify-otp")
    public Map<String, Object> verifyOtp(@RequestBody Map<String, String> req) {
        boolean valid = otpSC.verifyOtp(req.get("phone"), req.get("otp"));
        return Map.of("verified", valid);
    }
    @PostMapping("/login")
    public Map<String, Object> login(
            @RequestBody LoginRequest req
    ) {

        AdminUser user =
                adminUserRepository
                        .findByUsername(
                                req.getUsername()
                        )
                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "Invalid username"
                                        )
                        );

        if (
                !passwordEncoder.matches(
                        req.getPassword(),
                        user.getPassword()
                )
        ) {

            throw new RuntimeException(
                    "Invalid password"
            );
        }

        String token =
                JwtUtil.generateToken(
                        user.getUsername()
                );

        Map<String, Object> res =
                new HashMap<>();

        res.put("token", token);

        return res;
    }
}