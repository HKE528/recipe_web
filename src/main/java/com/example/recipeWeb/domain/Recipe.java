package com.example.recipeWeb.domain;

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
    private int id;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "recipe", cascade = CascadeType.ALL)
    private RecipeInfo recipeInfo;
}
