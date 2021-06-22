package com.example.recipeWeb.controller;

import com.example.recipeWeb.DTO.MemberDTO;
import com.example.recipeWeb.DTO.MyRecipesDTO;
import com.example.recipeWeb.DTO.RecipeDTO;
import com.example.recipeWeb.service.MemberService;
import com.example.recipeWeb.service.MyRecipeService;
import com.example.recipeWeb.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;
    private final MemberService memberService;
    private final MyRecipeService myRecipeService;

    @GetMapping("recipe/{memberId}/my")
    public String myRecipe(@Nullable @PathVariable("memberId") String memberId, Model model) {
        List<MyRecipesDTO> allMyRecipe = myRecipeService.findAllMyRecipe(memberId);

        MemberDTO memberDTO = memberService.findOne(memberId);

        List<RecipeDTO> recipeList = new ArrayList<>();
        for(MyRecipesDTO dto : allMyRecipe) {
            recipeList.add(dto.getRecipeDTO());
        }

        model.addAttribute("member", memberDTO);
        model.addAttribute("recipes", recipeList);

        return "recipe/myRecipe";
    }

    @GetMapping("recipe/{memberId}/my/add")
    public String addRecipe(@PathVariable("memberId") String memberId, Model model) {
        model.addAttribute("id", memberId);

        return "recipe/addRecipeForm";
    }

    @PostMapping("recipe/{memberId}/my/add")
    public String add(@PathVariable("memberId") String memberId, @ModelAttribute("form")RecipeDTO recipeDTO) {
        int myRecipe = myRecipeService.createMyRecipe(memberId, recipeDTO);

        return "redirect:/recipe/"+ memberId + "/my";
    }
}
