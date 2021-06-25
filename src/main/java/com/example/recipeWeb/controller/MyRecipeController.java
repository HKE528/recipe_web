package com.example.recipeWeb.controller;

import com.example.recipeWeb.DTO.MemberDTO;
import com.example.recipeWeb.DTO.MyRecipesDTO;
import com.example.recipeWeb.DTO.RecipeDTO;
import com.example.recipeWeb.domain.Category;
import com.example.recipeWeb.exception.NotExistRecipe;
import com.example.recipeWeb.service.MemberService;
import com.example.recipeWeb.service.MyRecipeService;
import com.example.recipeWeb.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.PackagePrivate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MyRecipeController {
    private final RecipeService recipeService;
    private final MemberService memberService;
    private final MyRecipeService myRecipeService;

    @GetMapping("recipe/{memberId}/my")
    public String myRecipe(@Nullable @PathVariable("memberId") String memberId, Model model) {
        List<MyRecipesDTO> allMyRecipe = myRecipeService.findAllMyRecipe(memberId);
        MemberDTO memberDTO = memberService.findOne(memberId);

/*        MemberDTO memberDTO = memberService.findOne(memberId);

        List<RecipeDTO> recipeList = new ArrayList<>();
        for(MyRecipesDTO dto : allMyRecipe) {
            recipeList.add(dto.getRecipeDTO());
        }

        model.addAttribute("member", memberDTO);
        model.addAttribute("recipes", recipeList);*/

        model.addAttribute("myRecipes", allMyRecipe);
        model.addAttribute("member", memberDTO);

        return "recipe/myRecipe";
    }

    @GetMapping("recipe/{memberId}/my/add")
    public String addRecipe(@PathVariable("memberId") String memberId, Model model) {
        model.addAttribute("id", memberId);
        model.addAttribute("recipeDTO", new RecipeDTO());

        return "recipe/addRecipeForm";
    }

    @PostMapping("recipe/{memberId}/my/add")
    public String add(@PathVariable("memberId") String memberId,
                      @Valid RecipeDTO recipeDTO,
                      BindingResult result,
                      Model model) {
        if(result.hasErrors()) {
            model.addAttribute("id", memberId);

            return "recipe/addRecipeForm";
        }

        int myRecipe = myRecipeService.createMyRecipe(memberId, recipeDTO);

        return "redirect:/recipe/"+ memberId + "/my";
    }

    @GetMapping("recipe/{memberId}/my/delete/{myRecipeId}")
    public String delete(
            @PathVariable("memberId") String memberId,
            @PathVariable("myRecipeId") int myRecipeId) {
        myRecipeService.deleteMyRecipe(myRecipeId);

        return "redirect:/recipe/" + memberId + "/my";
    }

    @GetMapping("recipe/{memberId}/my/{cate}")
    public String showCategory(@PathVariable("memberId") String memberId,
                               @PathVariable("cate") String cate,
                               Model model) {
        Category category = switch (cate) {
            case "ko" -> Category.KOREAN;
            case "jp" -> Category.JAPANESE;
            case "ch" -> Category.CHINESE;
            case "we" -> Category.WESTERN;
            default -> Category.OTHERS;
        };

        List<MyRecipesDTO> myRecipesDTO = myRecipeService.findAllMyRecipeByCategory(memberId, category);
        MemberDTO memberDTO = memberService.findOne(memberId);

        model.addAttribute("myRecipes", myRecipesDTO);
        model.addAttribute("member", memberDTO);

        return "recipe/myRecipe";
    }

    @GetMapping("/recipe/{memberId}/view/{myRecipeId}")
    public String showRecipe(
            @PathVariable("memberId") String memberId,
            @PathVariable("myRecipeId") int myRecipeId,
            Model model) {
        //MemberDTO memberDTO = memberService.findOne(memberId);
        MyRecipesDTO myRecipeDTO = myRecipeService.findMyRecipe(myRecipeId);

        //model.addAttribute("member", memberDTO);
        model.addAttribute("myRecipe", myRecipeDTO);

        String nlString = System.getProperty("line.separator").toString();
        model.addAttribute("nlString", nlString);

        return "recipe/showMyRecipe";
    }

    @GetMapping("/recipe/{memberId}/edit/{myRecipeId}")
    public String editRecipeForm(
            @PathVariable("memberId") String memberId,
            @PathVariable("myRecipeId") int myRecipeId,
            Model model) {

        MyRecipesDTO myRecipeDTO = myRecipeService.findMyRecipe(myRecipeId);
        model.addAttribute("memberId", memberId);
        model.addAttribute("myRecipeId", myRecipeId);
        model.addAttribute("myRecipe", myRecipeDTO.getRecipeDTO());

        return "recipe/editRecipeForm";
    }

    @PostMapping("/recipe/{memberId}/edit/{myRecipeId}")
    public String add(
            @PathVariable("memberId") String memberId,
            @PathVariable("myRecipeId") int myRecipeId,
            @Valid RecipeDTO recipeDTO,
            BindingResult result,
            Model model) {

        if(result.hasErrors()) {
            model.addAttribute("memberId", memberId);
            model.addAttribute("myRecipeId", myRecipeId);

            return "recipe/editRecipeForm";
        }

        recipeService.updateRecipe(recipeDTO);

        return "redirect:/recipe/" + memberId + "/view/" + myRecipeId;
    }
}
