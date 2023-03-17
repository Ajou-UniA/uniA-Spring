package com.example.member.controller;

import com.example.member.dto.MemberDTO;
import com.example.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/member")
public class MemberController {

    private final MemberService memberService;

    /*
        회원가입 로직 - POST
        (추가 기능 구현 예정 - 민석)
     */
    @PostMapping("/save")
    public ResponseEntity<MemberDTO> save(@ModelAttribute MemberDTO memberDTO){
        memberService.save(memberDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(memberDTO);
    }

    /*
        이메일 중복확인
     */
    @GetMapping("/save/exists/{memberEmail}")
    public ResponseEntity<Boolean> checkEmailDuplicate(@PathVariable String memberEmail){
        return ResponseEntity.ok(memberService.checkEmailDuplicate(memberEmail));
    }

    /*
         사용자가 조회하는 상세 페이지
     */
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDTO> findByMemberId(@PathVariable Long memberId){
        MemberDTO memberDTO = memberService.findById(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(memberDTO);
    }

    /*
        사용자 정보 업데이트, 추후 PatchMapping으로 수정 예정
     */
    @PatchMapping("/{memberId}")
    public ResponseEntity update(@PathVariable Long memberId, @ModelAttribute MemberDTO memberDTO){
        memberService.update(memberDTO);
        return ResponseEntity.status(HttpStatus.OK).body(memberDTO);
    }

    /*
        회원 탈퇴 기능 (DeleteMapping으로 수정 해야함)
        탈퇴 처리 후 index 페이지로 이동
     */
    @DeleteMapping("/{memberId}")
    public ResponseEntity deleteById(@PathVariable Long memberId){
        memberService.deleteById(memberId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/logout")
    public ResponseEntity logout(HttpSession session){
        session.invalidate();
        return new ResponseEntity<>("로그아웃 성공", HttpStatus.OK);
    }
}
