package com.example.member.service;

import com.example.member.entity.Role;
import lombok.RequiredArgsConstructor;
import com.example.member.dto.MemberDTO;
import com.example.member.entity.MemberEntity;
import com.example.member.repository.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Transactional
    public void save(MemberDTO memberDTO) {
        //시큐리티 비교에 암호화된 비밀번호가 필요하여 회원가입시 암호화하여 저장(보안+기능구현을 위함)
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDTO.setMemberPassword(passwordEncoder.encode(memberDTO.getMemberPassword()));
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);

    }

//    public MemberDTO login(String userEmail,String passWord) {
//
//
//        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(userEmail);
//        //isPresent() bool함수로 구현
//        if (byMemberEmail.isPresent()){
//            MemberEntity memberEntity = byMemberEmail.get();
//            if (memberEntity.getMemberPassword().equals(passWord)){
//                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
//                return dto;
//            } else{
//                return null;
//            }
//        } else {
//            return null;
//        }
//    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (MemberEntity memberEntity: memberEntityList){
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));
        }
        return memberDTOList;
    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()) {
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }

    public MemberDTO findByMemberEmail(String memberEmail){
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);
        if (optionalMemberEntity.isPresent()){
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }

    public MemberDTO updateForm(String myEmail){
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(myEmail);
        if (optionalMemberEntity.isPresent()){
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else{
            return null;
        }
    }


    public void update(MemberDTO memberDTO) {
        // update라는 메소드가 따로 없기 때문에, save 메소드 사용
        memberRepository.save(MemberEntity.toMemberEntity(memberDTO));
    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    public void deleteByMemberEmail(String memberEmail){
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);
        if (optionalMemberEntity.isPresent()){
            memberRepository.deleteById(optionalMemberEntity.get().getMemberId());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MemberEntity> findName = memberRepository.findByMemberEmail(username);
        MemberEntity member = findName.get();

        List<GrantedAuthority> authorities = new ArrayList<>();
//        if(("admin").equals(username)){
//            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
//        }else {
//            authorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
//        }

        return new User(member.getMemberEmail(), member.getMemberPassword() , authorities);
    }

}
