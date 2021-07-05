package com.example.recipeWeb.service;

import com.example.recipeWeb.domain.Member;
import com.example.recipeWeb.domain.MemberInfo;
import com.example.recipeWeb.domain.Role;
import com.example.recipeWeb.domain.dto.MemberDTO;
import com.example.recipeWeb.domain.enums.RoleEnum;
import com.example.recipeWeb.repository.MemberRepository;
import com.example.recipeWeb.repository.RoleRepository;
import javassist.bytecode.DuplicateMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void joinMember(MemberDTO dto) throws DuplicateMemberException {
        if(checkIsDup(dto.getUsername())) {
            throw new DuplicateMemberException("중복된 Username");
        }

        MemberInfo memberInfo = MemberInfo.createMemberInfo(dto);

        if(roleRepository.findByName(RoleEnum.ROLE_USER) == null) {
            roleRepository.save(Role.createUserRole());
            roleRepository.save(Role.createAdminRole());
        }

        Role role = roleRepository.findByName(RoleEnum.ROLE_USER);

        String encode = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(encode);

        Member member = Member.createMember(dto, memberInfo, role);

        memberRepository.save(member);
    }

    public MemberDTO findMember(String username) {
        Member member = memberRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);

        MemberDTO memberDTO = MemberDTO.generateDTO(member);

        return memberDTO;
    }

    @Transactional
    public void updateMember(MemberDTO dto) {
        Member member = memberRepository.findByUsername(dto.getUsername()).orElseThrow(NoSuchElementException::new);

        String encode = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(encode);

        member.changeData(dto);
    }

    @Transactional
    public void deleteMember(String username) {
        Member member = memberRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);

        memberRepository.delete(member);
    }

    private boolean checkIsDup(String username) {
        Member member = memberRepository.findByUsername(username).orElse(null);

        return member != null;
    }

}
