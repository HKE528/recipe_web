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
    private MemberDTO memberDTO;
    private RecipeDTO recipeDTO;

    public MyRecipesDTO() {
    }

    public MyRecipesDTO(int id, LocalDate addDate) {
        this.id = id;
        this.addDate = addDate;
    }

    public MyRecipesDTO(int id, LocalDate addDate, MemberDTO memberDTO, RecipeDTO recipeDTO) {
        this.id = id;
        this.addDate = addDate;
        this.memberDTO = memberDTO;
        this.recipeDTO = recipeDTO;
    }

    public static MyRecipesDTO createDTO(MyRecipes myRecipes){
        return new MyRecipesDTO(
                myRecipes.getId(),
                myRecipes.getAddDate(),
                MemberDTO.createDTO(myRecipes.getMember()),
                RecipeDTO.createDTO(myRecipes.getRecipe())
        );
    }

}
