package com.example.recipeWeb.domain.dto;

import com.example.recipeWeb.domain.Category;
import com.example.recipeWeb.domain.Member;
import com.example.recipeWeb.domain.Recipe;
import com.example.recipeWeb.domain.RecipeInfo;
import com.example.recipeWeb.domain.enums.CategoryEnum;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class RecipeDTO {

    private Long id;
    private String name;

    @Size(min = 1, max = 2000, message = "1 ~ 2000자 내외로 작성해 주세요")
    private String ingredient;

    @Size(min = 1, max = 5000, message = "1 ~ 5000자 내외로 작성해 주세요")
    private String description;

    @Size(max = 50, message = "50글자 이하로 작성해 주세요")
    private String comment;

    private boolean shareable = false;
    private boolean isFavorite = false;

    private String username;

    private CategoryEnum category;
    private LocalDate date;
    private int view = 0;
    private int shared = 0;

    private String imgPath;

    public RecipeDTO() {
    }

    public RecipeDTO(String name, String ingredient, String description, String comment, CategoryEnum category) {
        this.name = name;
        this.ingredient = ingredient;
        this.description = description;
        this.comment = comment;
        this.category = category;
    }

    public RecipeDTO(Long id, String name, String ingredient, String description, String comment, boolean shareable, CategoryEnum category, LocalDate date, int view, int shared) {
        this.id = id;
        this.name = name;
        this.ingredient = ingredient;
        this.description = description;
        this.comment = comment;
        this.shareable = shareable;
        this.category = category;
        this.date = date;
        this.view = view;
        this.shared = shared;
    }

    public static RecipeDTO generateDTO(Recipe recipe) {
        RecipeDTO recipeDTO = new RecipeDTO(
                recipe.getId(),
                recipe.getName(),
                recipe.getIngredient(),
                recipe.getDescription(),
                recipe.getComment(),
                recipe.isShareable(),
                recipe.getCategory(),
                recipe.getRecipeInfo().getDate(),
                recipe.getRecipeInfo().getView(),
                recipe.getRecipeInfo().getShared()
        );
        recipeDTO.setUsername(recipe.getMember().getUsername());

        return recipeDTO;
    }
}
