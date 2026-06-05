package com.app.okhrestaurant.service.sms.impl;

import com.app.okhrestaurant.service.sms.SmsSC;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.http.*;

@Service
@RequiredArgsConstructor
public class SmsSCImpl implements SmsSC {

    @Value("${zenziva.userkey}")
    private String userkey;

    @Value("${zenziva.apikey}")
    private String apikey;

    @Value("${zenziva.brand}")
    private String brand;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void sendOtp(String phone, String code) {

        phone = normalizePhone(phone);

        String url =
                "https://console.zenziva.net/waofficial/api/sendWAOfficial/";

        MultiValueMap<String, String> body =
                new LinkedMultiValueMap<>();

        body.add("userkey", userkey);
        body.add("passkey", apikey);
        body.add("to", phone);
        body.add("brand", brand);
        body.add("otp", code);

        HttpHeaders headers =
                new HttpHeaders();

        headers.setContentType(
                MediaType.APPLICATION_FORM_URLENCODED
        );

        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(
                        url,
                        request,
                        String.class
                );

        System.out.println(response.getBody());
    }

    private String normalizePhone(String phone) {

        phone = phone.replaceAll("[^0-9]", "");

        if (phone.startsWith("0")) {
            phone = "62" + phone.substring(1);
        }

        return phone;
    }
}