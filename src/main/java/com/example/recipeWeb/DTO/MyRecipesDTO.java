package com.example.recipeWeb.DTO;

import com.example.recipeWeb.domain.Member;
import com.example.recipeWeb.domain.MyRecipes;
import com.example.recipeWeb.domain.Recipe;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MyRecipesDTO {
    private int id;
    private LocalDate addDate;
    private Member member;
    private Recipe recipe;

    public MyRecipesDTO() {
    }

    public MyRecipesDTO(int id, LocalDate addDate) {
        this.id = id;
        this.addDate = addDate;
    }

    public MyRecipesDTO(int id, LocalDate addDate, Member member, Recipe recipe) {
        this.id = id;
        this.addDate = addDate;
        this.member = member;
        this.recipe = recipe;
    }

    public MyRecipesDTO(MyRecipes myRecipes) {
        this.id = myRecipes.getId();
        this.addDate = myRecipes.getAddDate();
        this.member = myRecipes.getMember();
        this.recipe = myRecipes.getRecipe();
    }
}
