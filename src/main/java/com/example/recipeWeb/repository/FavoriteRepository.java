package com.example.recipeWeb.repository;

import com.example.recipeWeb.domain.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByRecipeIdAndMemberId(Long recipeId, Long memberId);
}
