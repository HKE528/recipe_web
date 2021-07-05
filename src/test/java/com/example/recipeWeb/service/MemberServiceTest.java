package com.example.recipeWeb.service;

import com.example.recipeWeb.domain.dto.MemberDTO;
import com.example.recipeWeb.domain.enums.RoleEnum;
import javassist.bytecode.DuplicateMemberException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class MemberServiceTest {
    @Autowired
    private MemberService memberService;
    
    @Test
    public void 회원가입_테스트() throws DuplicateMemberException {
        //given
        String username = "joinTest";
        MemberDTO dto1 = generateTestDTO(username);

        //when
        memberService.joinMember(dto1);

        //then
        MemberDTO result = memberService.findMember(username);
        assertEquals(username, result.getUsername());
        assertEquals(dto1.getEmail(), result.getEmail());
    }

    @Test(expected = DuplicateMemberException.class)
    public void 중복_회원가입_테스트() throws DuplicateMemberException {
        //given
        String username = "dupTest";
        MemberDTO dto1 = generateTestDTO(username);
        MemberDTO dto2 = generateTestDTO(username);

        //when
        memberService.joinMember(dto1);
        memberService.joinMember(dto2);

        //then
        fail("중복 회원 예외가 발생해야 합니다.");
    }

    @Test(expected = NoSuchElementException.class)
    public void 없는회원_불러오기_테스트() {
        //given
        String username = "notExistTest";

        //when
        memberService.findMember(username);

        //then
        fail("예외가 발생해야 합니다.");
    }

    @Test(expected = NoSuchElementException.class)
    public void 회원_삭제_테스트() {
        //given
        String username = "deleteTest";
        MemberDTO dto1 = generateTestDTO(username);

        //when
        memberService.deleteMember(username);
        memberService.findMember(username);

        //then
        fail("예외가 발생해야 합니다.");
    }

    @Test
    public void 회원정보_업데이트_테스트() throws DuplicateMemberException {
        //given
        String username = "updateTest";
        MemberDTO dto1 = generateTestDTO(username);

        //when
        memberService.joinMember(dto1);

        String nick = "updateNick";
        String email = "updateEmail";
        String password = "updatePassword";
        dto1.setNickname(nick);
        dto1.setEmail(email);
        dto1.setPassword(password);

        memberService.updateMember(dto1);

        //then
        MemberDTO result = memberService.findMember(username);
        assertEquals(result.getNickname(), nick);
        assertEquals(result.getEmail(), email);
        assertEquals(password, result.getPassword());
    }

    private MemberDTO generateTestDTO() {
        MemberDTO memberDTO = new MemberDTO(
                "username",
                "password",
                true,
                "nickname",
                "email@test.com"
        );
        memberDTO.setOtherData(true);

        return memberDTO;
    }

    private MemberDTO generateTestDTO(String username) {
        MemberDTO memberDTO = generateTestDTO();
        memberDTO.setUsername(username);

        return memberDTO;
    }
}