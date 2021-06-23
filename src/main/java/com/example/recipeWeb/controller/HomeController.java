package com.example.recipeWeb.controller;

import com.example.recipeWeb.DTO.MemberDTO;
import com.example.recipeWeb.DTO.RecipeDTO;
import com.example.recipeWeb.domain.Category;
import com.example.recipeWeb.exception.DupIdException;
import com.example.recipeWeb.service.MemberService;
import com.example.recipeWeb.service.MyRecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final MemberService memberService;
    private final MyRecipeService myRecipeService;

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

        List<RecipeDTO> list = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            list.add(generateRecipeDTO(Category.KOREAN));
            list.add(generateRecipeDTO(Category.JAPANESE));
            list.add(generateRecipeDTO(Category.CHINESE));
            list.add(generateRecipeDTO(Category.WESTERN));
            list.add(generateRecipeDTO(Category.OTHERS));
        }

        try{
            memberService.join(memberDTO);
            for(RecipeDTO item : list){
                myRecipeService.createMyRecipe(memberDTO.getId(), item);
            }

            System.out.println("테스트 데이터 생성");
        } catch (DupIdException e) {
            return;
        }
    }

    private RecipeDTO generateRecipeDTO(Category category) {
        return new RecipeDTO(
                "test",
                category,
                "test",
                "etst",
                "test"
        );
    }
}
