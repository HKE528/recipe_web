package com.example.recipeWeb.controller;

import com.example.recipeWeb.domain.dto.MemberDTO;
import com.example.recipeWeb.service.MemberService;
import javassist.bytecode.DuplicateMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {

        return "member/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("memberDTO", new MemberDTO());

        return "member/register";
    }

    @PostMapping("/register")
    public String register(MemberDTO dto, BindingResult result, Model model) {

        try {

            memberService.joinMember(dto);
        } catch (DuplicateMemberException e) {
            result.rejectValue("username", "key", "중복된 아이디 입니다." );

            return "member/register";
        }

        return "redirect:/member/login";
    }

    @GetMapping("/mypage")
    public String myPage(Principal principal, Model model) {
        String username = principal.getName();

        MemberDTO member = memberService.findMember(username);
        model.addAttribute("member", member);

        return "member/myPage";
    }

    @PostMapping("/mypage")
    public String myPage(MemberDTO member) {
        memberService.updateMember(member);

        return "redirect:/";
    }

    @RequestMapping("/drop")
    public String drop(Principal principal) {
        String username = principal.getName();

        memberService.deleteMember(username);

        return "redirect:/member/login";
    }
}
