package com.example.recipeWeb.domain.dto;

import com.example.recipeWeb.domain.Favorite;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class FavoriteDTO {
    private Long id;
    private RecipeDTO recipe;
    private LocalDate date;

    public FavoriteDTO() {
    }

    public FavoriteDTO(Long id, RecipeDTO recipe, LocalDate date) {
        this.id = id;
        this.recipe = recipe;
        this.date = date;
    }
}
