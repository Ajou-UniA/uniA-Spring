package com.example.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class VerificationService {
    private final JavaMailSender javaMailSender;
    private Map<String, String> verificationCodes = new HashMap<>();

    public void sendVerificationCode(String memberEmail) {
        String verificationCode = generateVerificationCode();

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(memberEmail);
            message.setSubject("Registration verificationCode");
            message.setText("VerificationCode: " + verificationCode);
            javaMailSender.send(message);
        } catch (Exception e){
            throw new RuntimeException(e);
        }

        verificationCodes.put(memberEmail, verificationCode);
    }

    public boolean verify(String memberEmail, String verificationCode){
        // savedCode : 발급받은 코드, verificationCode : 사용자가 입력한 코드
        String savedCode = verificationCodes.get(memberEmail);
        if(savedCode == null){
            return false;
        }
        if(savedCode.equals(verificationCode)){
            verificationCodes.remove(memberEmail);
            return true;
        }
        return false;
    }
    private String generateVerificationCode(){
        Random random = new Random();
        int code = 1000 + random.nextInt(9000);
        return String.valueOf(code);
    }
}