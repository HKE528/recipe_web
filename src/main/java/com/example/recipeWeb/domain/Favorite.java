package com.example.recipeWeb.domain;

import lombok.Getter;

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
}
