package com.example.member.controller;

import com.example.member.dto.MemberDTO;
import com.example.member.service.MemberService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/member")
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입
     * [POST] /member/create
     * @param memberDTO
     * @param bindingResult
     * @return ResponseEntity
     */
    @PostMapping("/create")
    public ResponseEntity create(@Valid @RequestBody MemberDTO memberDTO, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(objectError -> {
                        FieldError fieldError = (FieldError) objectError;
                        return "Error field : " + fieldError.getField() + "\nError message : " + objectError.getDefaultMessage();
                    })
                    .collect(Collectors.joining("\n"));

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        memberService.create(memberDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(memberDTO);
    }

    /**
     * 이메일 중복 확인
     * [GET] /member/checkEmailDuplicate/{memberEmail}
     * @param memberEmail
     * @return ResponseEntity
     */
    @GetMapping("/checkEmailDuplicate/{memberEmail}")
    public ResponseEntity checkEmailDuplicate(@PathVariable String memberEmail){

        if(memberService.checkEmailDuplicate(memberEmail)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The email already exists");
        }
        return ResponseEntity.status(HttpStatus.OK).body("The email is available");
    }

    /**
     * DB에 저장된 회원 전체 조회, 실제 기능 아님
     * [GET] /member/list
     * @return ResponseEntity<List<MemberDTO>>
     */
    @GetMapping("/list")
    public ResponseEntity<List<MemberDTO>> findAll(){
        List<MemberDTO> memberDTOList = memberService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(memberDTOList);
    }

    /**
     * 마이페이지 조회
     * [GET] /member/{memberId}
     * @param memberId
     * @return ResponseEntity<MemberDTO>
     */
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDTO> findByMemberId(@PathVariable Long memberId){
        MemberDTO memberDTO = memberService.findById(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(memberDTO);
    }

    /**
     * 비밀번호 변경
     * [PATCH] /member/{memberId}
     * @param memberId
     * @param memberDTO
     * @return ResponseEntity<MemberDTO>
     */
    @PatchMapping("/{memberId}")
    public ResponseEntity<MemberDTO> update(@PathVariable Long memberId, @RequestBody MemberDTO memberDTO){

        String password = memberDTO.getMemberPassword();

        if (password == null || password.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        MemberDTO existMember = memberService.findById(memberId);

        if (existMember == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        existMember.setMemberPassword(password);
        memberService.update(existMember);
        return ResponseEntity.status(HttpStatus.OK).body(existMember);

    }


    /**
     * 회원 탈퇴
     * [DELETE] /member/{memberId}
     * @param memberId
     * @return ResponseEntity
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