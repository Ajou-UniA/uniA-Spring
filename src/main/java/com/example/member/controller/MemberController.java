package com.example.member.controller;

import com.example.member.dto.MemberDTO;
import com.example.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/member")
public class MemberController {

    private final MemberService memberService;


    /*
        로그인 페이지 GET 요청
    */
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    /*
        로그인 로직 - POST
        로그인 성공 후 main 페이지로 이동
        (추가 기능 구현 예정 - 승준)
     */
    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session){
       MemberDTO loginResult = memberService.login(memberDTO);
       if(loginResult != null) {
           // login 성공
           session.setAttribute("loginEmail", loginResult.getMemberEmail());
           return "main";
       } else{
           // login 실패
           return "login";
       }
    }



    /*
        회원가입 페이지 GET 요청
     */
    @GetMapping("/save")
    public String saveForm(){
        return "save";
    }

    /*
        회원가입 로직 - POST
        회원가입 성공 후 index 페이지로 이동
        (추가 기능 구현 예정 - 민석)
     */
    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO){
        memberService.save(memberDTO);
        return "index";
    }

    /*
        DB에 저장된 회원 전체 조회
        실제 기능은 아니고 개발할 때 DB 관리를 위해 만들었습니다
        회원들의 정보가 데이터베이스에 잘 저장되는지 프론트에서 확인
     */
    @GetMapping("/list")
    public String findAll(Model model){
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList", memberDTOList);
        return "list";
    }

    /*
        DB에 저장된 회원 한 명 조회
        실제 기능은 아니고 개발할 때 DB 관리를 위해 만들었습니다
     */
    @GetMapping("/list/{id}")
    public String findById(@PathVariable Long id, Model model){
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "detail";
    }

    /*
         사용자가 조회하는 상세 페이지 (실제 기능)
     */
    @GetMapping("/{memberEmail}")
    public String findByMemberEmail(@PathVariable String memberEmail, Model model){
        MemberDTO memberDTO = memberService.findByMemberEmail(memberEmail);
        model.addAttribute("member", memberDTO);
        return "userdata";
    }

    @GetMapping("/update")
    public String updateForm(HttpSession session, Model model){
        String myEmail = (String) session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.updateForm(myEmail);
        model.addAttribute("updateMember", memberDTO);
        return "update";
    }

    /*
        사용자 정보 업데이트, 추후 PatchMapping으로 수정 예정
     */
    @PostMapping("/update")
    public String update(@ModelAttribute MemberDTO memberDTO){
        memberService.update(memberDTO);
        return "redirect:/member/" + memberDTO.getMemberEmail();
    }

    /*
        회원 전체 조회에서의 삭제 기능 (실제 기능 X)
     */
    @GetMapping("/list/delete/{id}")
    public String deleteById(@PathVariable Long id){
        memberService.deleteById(id);
        return "redirect:/member/list";
    }

    /*
        회원 탈퇴 기능 (DeleteMapping으로 수정 해야함)
        탈퇴 처리 후 index 페이지로 이동
     */
    @GetMapping("/delete/{memberEmail}")
    public String deleteByMemberEmail(@PathVariable String memberEmail){
        memberService.deleteByMemberEmail(memberEmail);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "index";
    }
}
