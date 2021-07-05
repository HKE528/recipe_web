package com.example.recipeWeb.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity @Getter
@Table(name = "tb_recipeinfo")
@DynamicUpdate
public class RecipeInfo {

    @Id @GeneratedValue
    @Column(name = "recipeinfo_id")
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "view_count", nullable = false)
    private int view = 0;

    @Column(name = "shared_count", nullable = false)
    private int shared = 0;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    public RecipeInfo() {
        this.date = LocalDate.now();
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
