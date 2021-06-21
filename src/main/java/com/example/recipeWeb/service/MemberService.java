package com.example.recipeWeb.service;

import com.example.recipeWeb.DTO.MemberDTO;
import com.example.recipeWeb.domain.Member;
import com.example.recipeWeb.exception.DupIdException;
import com.example.recipeWeb.repository.MemberRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    //회원가입
    @Transactional
    public String join(MemberDTO memberDTO) {
        validateDupMember(memberDTO.getId());
        Member member = new Member(memberDTO);

        memberRepository.save(member);

        return memberDTO.getId();
    }

    //회원 찾기
    public MemberDTO findOne(String id) {
        Member member = memberRepository.findOne(id);

        MemberDTO memberDTO = generateDTO(member);

        return memberDTO;
    }

    public MemberDTO findOneWithPw(String id, String pw) {
        MemberDTO memberDTO = null;

        try{
            Member member = memberRepository.findOne(id, pw);
            memberDTO = generateDTO(member);

        } catch (EmptyResultDataAccessException e) {
            memberDTO = null;
        }

        return memberDTO;
    }

    private MemberDTO generateDTO(Member member) {
        return new MemberDTO(
                member.getId(),
                member.getPw(),
                member.getName(),
                member.getEmail()
        );
    }

    //아이디 중복 체크
    private void validateDupMember(String id) {
        Member member = memberRepository.findOne(id);

        if(member != null) {
            throw new DupIdException("이미 존재하는 아이디 입니다.");
        }
    }
}
