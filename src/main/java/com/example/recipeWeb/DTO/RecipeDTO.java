package com.example.recipeWeb.DTO;

import com.example.recipeWeb.domain.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
public class RecipeDTO {
    private String name;
    private Category category;
    private String ingredient;
    private String description;
    private boolean shared = false;

    public RecipeDTO() {

    }

    public RecipeDTO(String name, Category category, String ingredient, String description) {
        this.name = name;
        this.category = category;
        this.ingredient = ingredient;
        this.description = description;
    }

    public RecipeDTO(String name, Category category, String ingredient, String description, boolean shared) {
        this.name = name;
        this.category = category;
        this.ingredient = ingredient;
        this.description = description;
        this.shared = shared;
    }
}
