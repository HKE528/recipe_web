package com.example.recipeWeb.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Table(name = "tb_myrecipe")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyRecipes {

    @Id @GeneratedValue
    @Column(name = "myrecipe_id")
    private int id;

    @Column(name = "add_date", nullable = false)
    private Date addDate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="recipe_id")
    private Recipe recipe;

    //

}
