package com.example.recipeWeb.controller;

import com.example.recipeWeb.DTO.MemberDTO;
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

        return "home";
    }
}
