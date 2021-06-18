package com.example.recipeWeb.service;

import com.example.recipeWeb.DTO.MemberDTO;
import com.example.recipeWeb.DTO.MyRecipesDTO;
import com.example.recipeWeb.DTO.RecipeDTO;
import com.example.recipeWeb.domain.Member;
import com.example.recipeWeb.domain.MyRecipes;
import com.example.recipeWeb.domain.Recipe;
import com.example.recipeWeb.repository.MemberRepository;
import com.example.recipeWeb.repository.MyRecipeRepository;
import com.example.recipeWeb.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MyRecipeService {
    private final MyRecipeRepository myRecipeRepository;
    private final RecipeRepository recipeRepository;
    private final MemberRepository memberRepository;
    private final RecipeService recipeService;

    public int createMyRecipe(String memberId, RecipeDTO recipeDTO) {
        int recipeId = recipeService.saveRecipe(recipeDTO);
        Recipe recipe = recipeRepository.findOne(recipeId);
        Member member = memberRepository.findOne(memberId);

        MyRecipes myRecipes = new MyRecipes(member, recipe);

        myRecipeRepository.save(myRecipes);

        return myRecipes.getId();
    }

    public MyRecipesDTO findMyRecipe(int id) {
        MyRecipes myRecipes = myRecipeRepository.findOne(id);

        MyRecipesDTO myRecipesDTO = new MyRecipesDTO(
                myRecipes.getId(),
                myRecipes.getAddDate(),
                myRecipes.getMember(),
                myRecipes.getRecipe()
        );

        return myRecipesDTO;
    }
}
