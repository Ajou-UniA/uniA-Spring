package com.example.member.dto;

import com.example.member.entity.MemberEntity;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class MemberDTO {

    @NotBlank(message = "Please enter your name")
    private String memberName; // 이름

    @NotNull(message = "Please enter your student ID")
    private Long memberId; // 학번

    @NotBlank(message = "Please enter your major")
    private String memberMajor; // 학과

    @NotBlank(message = "Please enter your email")
    @Pattern(regexp = "[a-zA-Z0-9._%+-]+@ajou.ac.kr$", message = "Email format is incorrect")
    private String memberEmail; // 이메일

    @NotBlank(message = "Please enter password")
    @Size(min = 8, max = 12, message = "Password must be between 8 and 12 characters")
    private String memberPassword; // 비밀번호

    public static MemberDTO toMemberDTO(MemberEntity memberEntity){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberName(memberEntity.getMemberName());
        memberDTO.setMemberId(memberEntity.getMemberId());
        memberDTO.setMemberMajor(memberEntity.getMemberMajor());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        return memberDTO;
    }
}
