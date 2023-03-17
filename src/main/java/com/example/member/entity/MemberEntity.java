package com.example.member.entity;


import com.example.member.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Setter
@Getter
@Table(name = "member_table")
public class MemberEntity {

    @Column
    private String memberName; // 이름
    @Id
    private Long memberId; // 학번
    @Column
    private String memberMajor; // 학과
    @Column(unique = true)
    private String memberEmail; // 이메일
    @Column
    private String memberPassword; // 비밀번호



    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberId(memberDTO.getMemberId());
        memberEntity.setMemberMajor(memberDTO.getMemberMajor());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        return memberEntity;
    }

}
