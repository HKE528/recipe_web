package com.example.recipeWeb.domain;

import com.example.recipeWeb.domain.enums.CategoryEnum;
import lombok.Getter;

import javax.persistence.*;

//@Entity
@Getter
@Table(name = "tb_category")
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "name", nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryEnum name = CategoryEnum.OTHERS;
}
