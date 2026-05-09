package com.app.okhrestaurant.service.sms.impl;

import com.app.okhrestaurant.service.sms.SmsSC;
import org.springframework.stereotype.Service;

@Service
public class SmsSCImpl implements SmsSC {

    @Override
    public void sendOtp(String phone, String code) {
        System.out.println("OTP untuk " + phone + " : " + code);
    }
}
