package com.example.recipeWeb.controller;

import com.example.recipeWeb.DTO.MemberDTO;
import com.example.recipeWeb.exception.DupIdException;
import com.example.recipeWeb.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final MemberService memberService;

    @RequestMapping("/")
    public String home(@RequestParam("id") @Nullable String id, Model model) {
        MemberDTO memberDTO = null;

        if(id != null)
        {
            log.info("Home!! " + id);

            memberDTO = memberService.findOne(id);
        } else {
            log.info("Home!!");
        }


        model.addAttribute("member", memberDTO);

        generateTestData();

        return "home";
    }

    private void generateTestData() {
        MemberDTO memberDTO = new MemberDTO(
                "test",
                "test",
                "test",
                "test@test,com"
        );

        try{
            memberService.join(memberDTO);
            System.out.println("테스트 데이터 생성");
        } catch (DupIdException e) {
            return;
        }
    }
}
