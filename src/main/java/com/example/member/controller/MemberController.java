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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    /**
     * 회원가입
     * [POST] /member/save
     * @param memberDTO
     * @param bindingResult
     * @return memberDTO
     */
    @PostMapping("/create")
    public ResponseEntity create(@Valid @RequestBody MemberDTO memberDTO, BindingResult bindingResult){

        if (!(memberDTO.getMemberPassword().equals(memberDTO.getMemberConfirmPassword()))){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords do not match");
        }

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

    //test를 위한 컨트롤러
    @GetMapping("/member/logout/success")
    public ResponseEntity test1() {
        Map<String,Object> map = new HashMap<>();
        map.put("result", 0);
        return new ResponseEntity(map, HttpStatus.OK);
    }
    //test를 위한 컨트롤러
    @GetMapping("/member/login/success")
    public ResponseEntity test2() {
        Map<String,Object> map = new HashMap<>();
        map.put("result", 1);
        return new ResponseEntity(map, HttpStatus.OK);
    }


    /**
     * 이메일 중복 확인
     * [GET] /member/email-check/{memberEmail}
     * @param memberEmail
     * @return ResponseEntity
     */
    @GetMapping("/email-check/{memberEmail}")
    public ResponseEntity checkEmailDuplicate(@PathVariable String memberEmail){

        if (memberEmail == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter your email");
        }

        if(memberService.checkEmailDuplicate(memberEmail)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The email already exists");
        }

        return ResponseEntity.status(HttpStatus.OK).body("The email is available");
    }

    /**
     * DB에 저장된 회원 전체 조회, 실제 기능 아님
     * [GET] /member/list
     * @return List<MemberDTO>
     */
    @GetMapping("/member/list")
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
    @GetMapping("/member/{memberId}")
    public ResponseEntity findByMemberId(@PathVariable Long memberId){
        MemberDTO memberDTO = memberService.findById(memberId);
        if (memberDTO == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Member does not exist");
        }
        return ResponseEntity.status(HttpStatus.OK).body(memberDTO);
    }

    /**
     * 비밀번호 변경
     * [PATCH] /member/{memberId}
     * @param memberId
     * @param memberDTO
     * @return ResponseEntity<MemberDTO>
     */
    @PatchMapping("/member/{memberId}")
    public ResponseEntity<MemberDTO> update(@PathVariable Long memberId, @RequestBody MemberDTO memberDTO) {

        String password = memberDTO.getMemberPassword();

        if (password == null || password.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        MemberDTO existMember = memberService.findById(memberId);

        if (existMember == null) {
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
     * @return
     */
    @DeleteMapping("/member/{memberId}")
    public ResponseEntity deleteById(@PathVariable Long memberId){
        memberService.deleteById(memberId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
