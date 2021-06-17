package com.example.recipeWeb.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Getter
@Table(name = "tb_myrecipe")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyRecipes {

    @Id @GeneratedValue
    @Column(name = "myrecipe_id")
    private int id;

    @Column(name = "add_date", nullable = false)
    private LocalDate addDate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="recipe_id")
    private Recipe recipe;

    public MyRecipes(Member member, Recipe recipe) {
        this.member = member;
        this.recipe = recipe;
        addDate = LocalDate.now();
    }

    //==연관 관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getMyRecipes().add(this);
    }
}
