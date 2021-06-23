package com.example.recipeWeb.DTO;

import com.example.recipeWeb.domain.Category;
import com.example.recipeWeb.domain.Recipe;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RecipeDTO {
    private String name;
    private Category category;
    private String ingredient;
    private String description;

    @Size(max = 100, message = "내용이 너무 길어요!")
    private String comment;
    private boolean shared = false;

    public RecipeDTO() {

    }

    public RecipeDTO(String name, Category category, String ingredient, String description) {
        this.name = name;
        this.category = category;
        this.ingredient = ingredient;
        this.description = description;
    }

    public RecipeDTO(String name, Category category, String ingredient, String description, String comment) {
        this.name = name;
        this.category = category;
        this.ingredient = ingredient;
        this.description = description;
        this.comment = comment;
    }

    public RecipeDTO(String name, Category category, String ingredient, String description, boolean shared) {
        this.name = name;
        this.category = category;
        this.ingredient = ingredient;
        this.description = description;
        this.shared = shared;
    }

    public RecipeDTO(String name, Category category, String ingredient, String description, String comment, boolean shared) {
        this.name = name;
        this.category = category;
        this.ingredient = ingredient;
        this.description = description;
        this.comment = comment;
        this.shared = shared;
    }

    public static RecipeDTO createDTO(Recipe recipe){
        return new RecipeDTO(
                recipe.getName(),
                recipe.getCategory(),
                recipe.getIngredient(),
                recipe.getDescription(),
                recipe.getComment(),
                recipe.isShared()
        );
    }
}
