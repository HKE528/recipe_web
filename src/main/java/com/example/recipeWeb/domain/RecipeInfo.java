package com.example.recipeWeb.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity @Getter
@Table(name = "tb_recipeinfo")
public class RecipeInfo {

    @Id @GeneratedValue
    @Column(name = "recipeinfo_id")
    private int id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "view_count", nullable = false)
    private int view = 0;

    @Column(name = "shared_count", nullable = false)
    private int shared = 0;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
}
