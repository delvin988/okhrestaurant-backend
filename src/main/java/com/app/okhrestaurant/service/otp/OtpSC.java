package com.app.okhrestaurant.service.otp;

public interface OtpSC {

    String sendOtp(String phone);

    boolean verifyOtp(String phone, String code);
    boolean isPhoneVerified(String phone);
}