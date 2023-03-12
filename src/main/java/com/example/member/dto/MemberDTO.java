package com.example.member.dto;

import com.example.member.entity.MemberEntity;
import lombok.Data;

@Data
public class MemberDTO {

    private String memberName; // 이름

    private Long memberId; // 학번

    private String memberMajor; // 학과

    private String memberEmail; // 이메일

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
