package com.app.okhrestaurant.service.otp.impl;

import com.app.okhrestaurant.entity.Otp;
import com.app.okhrestaurant.repository.OtpRP;
import com.app.okhrestaurant.service.otp.OtpSC;
import com.app.okhrestaurant.service.sms.SmsSC;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpSCImpl implements OtpSC {

    private final OtpRP otpRP;
    private final SmsSC smsSc;

    @Override
    public String sendOtp(String phone) {

        String code = generateOtp();

        Otp otp = Otp.builder()
                .phone(phone)
                .code(code)
                .expiredAt(
                        LocalDateTime.now()
                                .plusMinutes(5)
                )
                .verified(false)
                .build();

        otpRP.save(otp);

        smsSc.sendOtp(phone, code);

        return code;
    }

    @Override
    public boolean verifyOtp(String phone, String inputCode) {
        Otp otp = otpRP.findTopByPhoneOrderByIdDesc(phone)
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (otp.isVerified()) return false;

        if (otp.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        if (!otp.getCode().equals(inputCode)) {
            return false;
        }

        otp.setVerified(true);
        otpRP.save(otp);

        return true;
    }

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    @Override
    public boolean isPhoneVerified(String phone) {
        return otpRP.findTopByPhoneOrderByIdDesc(phone)
                .map(otp -> otp.isVerified() && otp.getExpiredAt().isAfter(LocalDateTime.now()))
                .orElse(false);
    }
}