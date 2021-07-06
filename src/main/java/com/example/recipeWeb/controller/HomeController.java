package com.example.recipeWeb.controller;

import com.example.recipeWeb.domain.Role;
import com.example.recipeWeb.domain.dto.RecipeDTO;
import com.example.recipeWeb.domain.enums.RoleEnum;
import com.example.recipeWeb.repository.RoleRepository;
import com.example.recipeWeb.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final RoleRepository roleRepository;
    private final RecipeService recipeService;

    @RequestMapping
    public String home() {
        return "redirect:/home/all";
    }

    @RequestMapping("/home/{cate}")
    public String home(@PathVariable("cate")String cate, Model model) {
        List<RecipeDTO> recipes = recipeService.findAllShardRecipe();

        model.addAttribute("recipes", recipes);
        model.addAttribute("selected", cate);

        return "home";
    }
}
