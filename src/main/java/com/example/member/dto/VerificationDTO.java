

package com.example.member.dto;

import lombok.Data;

@Data
public class VerificationDTO {
    private String email;
    private String verificationCode;
}
