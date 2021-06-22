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

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyRecipeService {
    private final MyRecipeRepository myRecipeRepository;
    private final RecipeRepository recipeRepository;
    private final MemberRepository memberRepository;
    private final RecipeService recipeService;

    @Transactional
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

        MyRecipesDTO myRecipesDTO = MyRecipesDTO.createDTO(myRecipes);

        return myRecipesDTO;
    }

    public List<MyRecipesDTO> findAllMyRecipe(String id) {
        Member member = memberRepository.findOne(id);
        List<MyRecipes> myRecipes = myRecipeRepository.findAll(member);

        List<MyRecipesDTO> myRecipesDTO = new ArrayList<>();
        for(MyRecipes myRecipe : myRecipes) {
            MyRecipesDTO dto = MyRecipesDTO.createDTO(myRecipe);

            myRecipesDTO.add(dto);
        }

        return myRecipesDTO;
    }
}
