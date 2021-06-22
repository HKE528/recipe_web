package com.example.recipeWeb.repository;

import com.example.recipeWeb.domain.Member;
import com.example.recipeWeb.domain.MyRecipes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MyRecipeRepository {
    private final EntityManager em;

    public void save(MyRecipes myRecipes) {
        em.persist(myRecipes);
    }

    public MyRecipes findOne(int id) {
        return em.find(MyRecipes.class, id);
    }

    public List<MyRecipes> findAll(Member member) {
        return em.createQuery("select mr from MyRecipes mr where mr.member = :member", MyRecipes.class)
                .setParameter("member", member)
                .getResultList();
    }

}
