package com.example.recipeWeb.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity @Getter
@Table(name = "tb_favorite")
public class Favorite {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @Column(nullable = false)
    private LocalDate date;

    public Favorite() {
        this.date = LocalDate.now();
    }

    public static Favorite createFavorite(Member member, Recipe recipe) {
        Favorite favorite = new Favorite();

        favorite.setRecipe(recipe);
        favorite.setMember(member);

        return favorite;
    }

    private void setMember(Member member) {
        this.member = member;
        member.getFavorites().add(this);
    }

    private void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
