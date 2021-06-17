package com.example.recipeWeb.service;

import com.example.recipeWeb.DTO.MemberDTO;
import com.example.recipeWeb.exception.DupIdException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Test
    public void 회원가입() {
        //given
        MemberDTO mem1 = generateMemberDTO();

        //when
        String id = memberService.join(mem1);

        //then
        MemberDTO result = memberService.findOne(id);
        assertEquals(mem1.getId(), result.getId());
    }

    @Test(expected = DupIdException.class)
    public void 중복_아이디_체크() {
        //given
        MemberDTO mem1 = generateMemberDTO();
        MemberDTO mem2 = generateMemberDTO();

        //when
        memberService.join(mem1);
        memberService.join(mem2);

        //then
        fail("중복아이디 예외가 발생해야 함");
    }

    private MemberDTO generateMemberDTO() {
        return new MemberDTO(
                "testId", "testPw", "testName", "testEmail"
        );
    }

    private MemberDTO generateMemberDTO(String id) {
        return new MemberDTO(
                id, "testPw", "testName", "testEmail"
        );
    }
}