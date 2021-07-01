package com.example.recipeWeb.service;

import com.example.recipeWeb.domain.Member;
import com.example.recipeWeb.domain.MemberInfo;
import com.example.recipeWeb.domain.Role;
import com.example.recipeWeb.domain.dto.MemberDTO;
import com.example.recipeWeb.repository.MemberRepository;
import javassist.bytecode.DuplicateMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void joinMember(MemberDTO dto) throws DuplicateMemberException {
        if(checkIsDup(dto.getUsername())) {
            throw new DuplicateMemberException("중복된 Username");
        }

        MemberInfo memberInfo = MemberInfo.createMemberInfo(dto);
        Role role = Role.createUserRole();

        Member member = Member.createMember(dto, memberInfo, role);

        memberRepository.save(member);
    }

    public MemberDTO findMember(MemberDTO dto) {
        Member member = memberRepository.findByUsername(dto.getUsername()).orElseThrow(NoSuchElementException::new);

        MemberDTO memberDTO = generateDTO(member);

        return memberDTO;
    }

    public void updateMember(MemberDTO dto) {
        Member member = memberRepository.findByUsername(dto.getUsername()).orElseThrow(NoSuchElementException::new);

        member.changeData(dto);
    }

    public void deleteMember(String username) {
        Member member = memberRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);

        memberRepository.delete(member);
    }

    private boolean checkIsDup(String username) {
        Member member = memberRepository.findByUsername(username).orElse(null);

        return member != null;
    }

    private MemberDTO generateDTO(Member member) {
        return new MemberDTO(
                member.getId(),
                member.getUsername(),
                member.getPassword(),
                member.getEnabled(),
                member.getMemberInfo().getNickname(),
                member.getMemberInfo().getEmail(),
                member.getMemberInfo().getJoindate()
        );
    }
}
