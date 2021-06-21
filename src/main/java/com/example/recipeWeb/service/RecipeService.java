package com.example.recipeWeb.service;

import com.example.recipeWeb.DTO.RecipeDTO;
import com.example.recipeWeb.domain.Recipe;
import com.example.recipeWeb.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;

    @Transactional
    public int saveRecipe(RecipeDTO recipeDTO) {
        Recipe recipe = new Recipe(recipeDTO);

        recipeRepository.save(recipe);

        return recipe.getId();
    }

    public RecipeDTO loadRecipe(int id) {
        Recipe recipe = recipeRepository.findOne(id);

        RecipeDTO recipeDTO = new RecipeDTO(
                recipe.getName(),
                recipe.getCategory(),
                recipe.getIngredient(),
                recipe.getDescription()
        );

        return recipeDTO;
    }
}
