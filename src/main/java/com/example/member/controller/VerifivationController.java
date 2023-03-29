package com.example.member.controller;

import com.example.member.dto.VerificationDTO;

import com.example.member.service.VerificationService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class VerifivationController {
    private final VerificationService verificationService;

    /**
     * 인증번호 전송
     * [GET] /member/verify/{memberEmail}
     * @param memberEmail
     * @return ResponseEntity
     */
    @GetMapping("/verify/{memberEmail}")
    public ResponseEntity send(@PathVariable String memberEmail){

        if (memberEmail == null || memberEmail.isEmpty()){ // 자바 17은 StringUtils.isBlank(memberEmail) 사용 가능
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        verificationService.sendVerificationCode(memberEmail);

        return ResponseEntity.status(HttpStatus.OK).body(null);

    }

    /**
     * 인증번호 확인
     * [POST] /member/verify
     * @param verificationDTO
     * @return ResponseEntity<?>
     */
    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody VerificationDTO verificationDTO){
        String email = verificationDTO.getEmail();
        String verificationCode = verificationDTO.getVerificationCode();
        boolean verified = verificationService.verify(email, verificationCode);
        if (verified){
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}