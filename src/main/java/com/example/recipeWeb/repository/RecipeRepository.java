package com.example.recipeWeb.repository;

import com.example.recipeWeb.domain.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor

public class RecipeRepository {
    private final EntityManager em;

    public void save(Recipe recipe) {
        em.persist(recipe);
    }

    public Recipe findOne(int id) {
        return em.find(Recipe.class, id);
    }

    public void deleteOne(Recipe recipe) {
        em.remove(recipe);
    }
}
