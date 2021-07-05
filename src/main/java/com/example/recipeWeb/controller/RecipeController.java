package com.example.recipeWeb.controller;

import com.example.recipeWeb.domain.dto.MemberDTO;
import com.example.recipeWeb.domain.dto.RecipeDTO;
import com.example.recipeWeb.service.MemberService;
import com.example.recipeWeb.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;
    private final MemberService memberService;

    @GetMapping("/my/{cate}")
    public String myRecipe(@PathVariable("cate") String cate, Principal principal, Model model) {
        String username = principal.getName();

        List<RecipeDTO> myRecipes = recipeService.findAllMyRecipe(username);

        model.addAttribute("recipes", myRecipes);
        model.addAttribute("selected", cate);

        return "recipe/myRecipe";
    }

    @GetMapping("/my/add")
    public String addRecipe(Model model) {
        model.addAttribute("dto", new RecipeDTO());

        return "recipe/addRecipeForm";
    }

    @PostMapping("/my/add")
    public String addRecipe(@Valid RecipeDTO dto, Principal principal, BindingResult result) {

        if(result.hasErrors()) {

            return "recipe/addRecipeForm";
        }

        String username = principal.getName();
        dto.setUsername(username);

        recipeService.saveRecipe(dto);

        return "redirect:/recipe/my/all";
    }
}
