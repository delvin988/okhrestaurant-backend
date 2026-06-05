package com.app.okhrestaurant.service.otp.impl;

import com.app.okhrestaurant.entity.Otp;
import com.app.okhrestaurant.repository.OtpRP;
import com.app.okhrestaurant.service.otp.OtpSC;
import com.app.okhrestaurant.service.sms.SmsSC;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpSCImpl implements OtpSC {

    private final OtpRP otpRP;
    private final SmsSC smsSc;

    @Override
    public String sendOtp(String phone) {
        long totalToday =
                otpRP.countByPhoneAndCreatedAtAfter(
                        phone,
                        LocalDate.now()
                                .atStartOfDay()
                );

        if (totalToday >= 10) {
            throw new RuntimeException(
                    "Daily OTP limit reached. Please try again tomorrow."
            );
        }
        Otp latestOtp =
                otpRP.findTopByPhoneOrderByIdDesc(phone)
                        .orElse(null);

        if (latestOtp != null &&
                latestOtp.getCreatedAt() != null &&
                latestOtp.getCreatedAt()
                        .plusSeconds(60)
                        .isAfter(LocalDateTime.now())) {

            throw new RuntimeException(
                    "Please wait 60 seconds before requesting another code"
            );
        }

        String code = generateOtp();

        Otp otp = Otp.builder()
                .phone(phone)
                .code(code)
                .createdAt(
                        LocalDateTime.now()
                )
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
    @Override
    public String normalizePhone(String phone) {

        phone = phone.replaceAll("[^0-9]", "");

        if (phone.startsWith("0")) {
            phone = "62" + phone.substring(1);
        }

        return phone;
    }
}