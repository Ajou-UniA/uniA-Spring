package com.example.member.controller;

import com.example.member.dto.MemberDTO;
import com.example.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/member")
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입
     * [POST] /member/save
     * @param memberDTO
     * @param bindingResult
     * @return memberDTO
     */
    @PostMapping("/save")
    public ResponseEntity save(@Valid @ModelAttribute MemberDTO memberDTO, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder(); // String 객체보다 좋음
            bindingResult.getAllErrors().forEach(objectError -> {
                FieldError field = (FieldError) objectError;
                String message = objectError.getDefaultMessage();

                sb.append("Error field : " + field.getField() + "\n");
                sb.append("Error message : " + message);
            });
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(sb.toString());
        }

        memberService.save(memberDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(memberDTO);
    }

    /**
     * 이메일 중복 확인
     * [GET] /member/save/{memberEmail}
     * @param memberEmail
     * @return
     */
    @GetMapping("/save/{memberEmail}")
    public ResponseEntity checkEmailDuplicate(@PathVariable String memberEmail){

        if(memberService.checkEmailDuplicate(memberEmail) == true){
            return new ResponseEntity<>("The email already exists", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Good", HttpStatus.OK);
    }

    /**
     * DB에 저장된 회원 전체 조회, 실제 기능 아님
     * [GET] /member/list
     * @return List<MemberDTO>
     */
    @GetMapping("/list")
    public ResponseEntity<List<MemberDTO>> findAll(){
        List<MemberDTO> memberDTOList = memberService.findAll();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(memberDTOList);
    }

    /**
     * 마이페이지 조회
     * [GET] /member/{memberId}
     * @param memberId
     * @return memberDTO
     */
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDTO> findByMemberId(@PathVariable Long memberId){
        MemberDTO memberDTO = memberService.findById(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(memberDTO);
    }

    /**
     * 회원정보 변경 -> *******비밀번호만 변경하도록 수정해야함*******
     * [PATCH] /member/{memberId}
     * @param memberId
     * @param memberDTO
     * @return memberDTO
     */
    @PatchMapping("/{memberId}")
    public ResponseEntity update(@PathVariable Long memberId, @ModelAttribute MemberDTO memberDTO){
        memberService.update(memberDTO);
        return ResponseEntity.status(HttpStatus.OK).body(memberDTO);
    }


    /**
     * 회원 탈퇴
     * [DELETE] /member/{memberId}
     * @param memberId
     * @return
     */
    @DeleteMapping("/{memberId}")
    public ResponseEntity deleteById(@PathVariable Long memberId){
        memberService.deleteById(memberId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 로그아웃 (Controller 내 구현 X)
     * [GET] /member/logout
     * @param
     * @return
     */
//    @GetMapping("/logout")
//    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//        }
//        return ResponseEntity.ok().build();
//    }
}