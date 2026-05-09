package com.app.okhrestaurant.controller;

import com.app.okhrestaurant.service.otp.OtpSC;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final OtpSC otpSC;

    @PostMapping("/send-otp")
    public Map<String, String> sendOtp(@RequestBody Map<String, String> req) {
        String otp = otpSC.sendOtp(req.get("phone"));
        return Map.of(
                "message", "OTP sent",
                "otp", otp
        );
    }

    @PostMapping("/verify-otp")
    public Map<String, Object> verifyOtp(@RequestBody Map<String, String> req) {
        boolean valid = otpSC.verifyOtp(req.get("phone"), req.get("otp"));
        return Map.of("verified", valid);
    }
}