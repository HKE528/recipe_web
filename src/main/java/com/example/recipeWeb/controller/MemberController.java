package com.example.recipeWeb.controller;

import com.example.recipeWeb.DTO.LoginDTO;
import com.example.recipeWeb.DTO.MemberDTO;
import com.example.recipeWeb.exception.DupIdException;
import com.example.recipeWeb.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String login(LoginDTO dto, BindingResult result, RedirectAttributes redirect) {
        MemberDTO memberDTO = memberService.findOneWithPw(dto.getId(), dto.getPw());

        if(memberDTO == null) {
            result.rejectValue("id", "key", "아이디 혹은 비밀번호가 틀리거나 존재하지 않습니다.");

            return "members/login";
        }

        redirect.addAttribute("id", memberDTO.getId());
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

    @GetMapping("member/{memberId}/myPage")
    public String myPageForm(@PathVariable("memberId") String id, Model model) {
        MemberDTO memberDTO = memberService.findOne(id);

        model.addAttribute("member", memberDTO);

        return "members/myPage";
    }

    @PostMapping("member/{memberId}/myPage")
    public String myPage(@PathVariable("memberId") String id,
                         @ModelAttribute("form") MemberDTO memberDTO,
                         RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("id", id);

        memberService.updateMember(id, memberDTO);

        return "redirect:/";
    }

}
