package com.example.recipeWeb.domain;

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
    private int id;

    @Column(name="recipe_name", length = 40, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(length = 2000)
    private String ingredient;

    @Column(length = 5000)
    private String description;

    @Column(nullable = false)
    private boolean shared = false;

    public Recipe(String name, Category category, String ingredient, String description, boolean shared) {
        this.name = name;
        this.category = category;
        this.ingredient = ingredient;
        this.description = description;
        this.shared = shared;
    }

    public Recipe(String name, Category category, String ingredient, String description) {
        this.name = name;
        this.category = category;
        this.ingredient = ingredient;
        this.description = description;
    }
}
