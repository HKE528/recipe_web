package com.example.recipeWeb.domain;

import com.example.recipeWeb.domain.dto.RecipeDTO;
import com.example.recipeWeb.domain.enums.CategoryEnum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "tb_recipe")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert @DynamicUpdate
public class Recipe {

    @Id @GeneratedValue
    @Column(name = "recipe_id")
    private Long id;

    @Column(name="recipe_name", length = 40, nullable = false)
    private String name;

    @Column(length = 2000)
    private String ingredient;

    @Column(length = 5000)
    private String description;

    @Column(length = 100)
    private String comment;

    @Column(nullable = false)
    private boolean shareable = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "category_id")
    @Enumerated(EnumType.STRING)
    private CategoryEnum category;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "recipe", cascade = CascadeType.ALL)
    private RecipeInfo recipeInfo;

    public void setShareable(boolean shareable) {
        this.shareable = shareable;
    }

    public Recipe(String name, String ingredient, String description, String comment, boolean shareable, CategoryEnum category) {
        this.name = name;
        this.ingredient = ingredient;
        this.description = description;
        this.comment = comment;
        this.shareable = shareable;
        this.category = category;
    }

    public static Recipe createRecipe(RecipeDTO dto, Member member, RecipeInfo recipeInfo) {
        Recipe recipe = new Recipe(
                dto.getName(),
                dto.getIngredient(),
                dto.getDescription(),
                dto.getComment(),
                dto.isShareable(),
                dto.getCategory()
        );
        
        recipe.setRecipeInfo(recipeInfo);
        recipe.setMember(member);

        return recipe;
    }

    public void changeData(RecipeDTO dto) {
        this.name = dto.getName();
        this.ingredient = dto.getIngredient();
        this.description = dto.getDescription();
        this.comment = dto.getComment();
        this.category = dto.getCategory();
    }

    private void setMember(Member member) {
        this.member = member;
        member.getRecipes().add(this);
    }

    private void setRecipeInfo(RecipeInfo recipeInfo) {
        this.recipeInfo = recipeInfo;
        recipeInfo.setRecipe(this);
    }
}
