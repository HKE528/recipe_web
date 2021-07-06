package com.example.recipeWeb.repository;

import com.example.recipeWeb.domain.Recipe;
import com.example.recipeWeb.domain.enums.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByShareable(boolean shareable);
}
