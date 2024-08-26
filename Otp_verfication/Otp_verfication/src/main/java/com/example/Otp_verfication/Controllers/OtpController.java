package com.example.Otp_verfication.Controllers;

import com.example.Otp_verfication.Impl.SmsService;
import com.example.Otp_verfication.services.Otpservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
public class OtpController {

    @Autowired
    private Otpservice otpService;

    @Autowired
    private SmsService smsService;

    @PostMapping("/generate")
    public String generateOtp(@RequestParam String phoneNumber) {
        String otp = otpService.generateOtp(phoneNumber);
        smsService.sendOtp(phoneNumber, otp);
        return "OTP sent to " + phoneNumber;
    }

    @PostMapping("/verify")
    public String verifyOtp(@RequestParam String phoneNumber, @RequestParam String otp) {
        boolean isValid = otpService.verifyOtp(phoneNumber, otp);
        if (isValid) {
            return "OTP verified successfully.";
        } else {
            return "Invalid or expired OTP.";
        }
    }
}

