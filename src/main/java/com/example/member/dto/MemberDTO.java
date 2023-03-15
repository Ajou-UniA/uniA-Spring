package com.example.member.dto;

import com.example.member.entity.MemberEntity;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class MemberDTO {

    @NotEmpty(message = "이름은 필수값입니다.")
    private String memberName; // 이름
    @Size(min = 9, message = "학번을 다시 확인해 주세요.")
    private Long memberId; // 학번

    private String memberMajor; // 학과

    @Email
    private String memberEmail; // 이메일

    @Size(min = 9, message = "비밀번호는 최소 9글자 이상이여야 합니다.")
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
