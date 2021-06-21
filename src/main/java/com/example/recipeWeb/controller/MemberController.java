package com.example.recipeWeb.controller;

import com.example.recipeWeb.DTO.LoginDTO;
import com.example.recipeWeb.DTO.MemberDTO;
import com.example.recipeWeb.exception.DupIdException;
import com.example.recipeWeb.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("member/login")
    public String loginForm(Model model){
        model.addAttribute("loginDTO", new LoginDTO());

        return "members/login";
    }

    @PostMapping("member/login")
    public String login(LoginDTO dto, Model model) throws Exception {
        MemberDTO memberDTO = memberService.findOneWithPw(dto.getId(), dto.getPw());

        if(memberDTO.getId() == null) {
            LoginDTO loginDTO = new LoginDTO();
            loginDTO.setLoginErr(true);

            model.addAttribute("loginDTO", loginDTO);

            return "members/login";
        } else {
            model.addAttribute("id", dto.getId());
        }

        return "redirect:/";
    }

    @GetMapping("member/new")
    public String joinForm(Model model){
        model.addAttribute("memberDTO", new MemberDTO());

        return "members/joinMemberForm";
    }

    @PostMapping("member/new")
    public String joinMember(MemberDTO dto, BindingResult result) {
        String action = "";

        try{
            memberService.join(dto);

            action = "redirect:/member/login";
        } catch (DupIdException e) {
            //model.addAttribute("preData", dto);
            result.rejectValue("id", "key", "이미 존재하는 아이디 입니다.");

            action = "members/joinMemberForm";
        }

        return action;
    }
/*
    @RequestMapping("member/joinErr")
    public String dupId(@Valid MemberDTO dto, @RequestParam("preData") MemberDTO data,
                        BindingResult result, Model model){
        result.addError();

        model.addAttribute("memberDTO", data);

        return "members/joinMemberForm";
    }*/
}
