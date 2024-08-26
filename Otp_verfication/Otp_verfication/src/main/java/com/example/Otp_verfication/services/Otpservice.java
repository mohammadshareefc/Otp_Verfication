package com.example.Otp_verfication.services;

import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class Otpservice {

    private static final int OTP_VALID_DURATION = 5 * 60 * 1000; // 5 minutes
    private final ConcurrentHashMap<String, OtpData> otpStorage = new ConcurrentHashMap<>();
    private final Random random = new Random();

    public String generateOtp(String key) {
        String otp = String.format("%04d", random.nextInt(10000));
        OtpData otpData = new OtpData(otp, System.currentTimeMillis() + OTP_VALID_DURATION);
        otpStorage.put(key, otpData);
        return otp;
    }

    public boolean verifyOtp(String key, String otp) {
        OtpData otpData = otpStorage.get(key);
        if (otpData == null || otpData.isExpired() || !otpData.getOtp().equals(otp)) {
            return false;
        }
        otpStorage.remove(key);
        return true;
    }

    private static class OtpData {
        private final String otp;
        private final long expiryTime;

        OtpData(String otp, long expiryTime) {
            this.otp = otp;
            this.expiryTime = expiryTime;
        }

        String getOtp() {
            return otp;
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }
    }
}
