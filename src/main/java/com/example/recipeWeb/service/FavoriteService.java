package com.example.recipeWeb.service;

import com.example.recipeWeb.domain.Favorite;
import com.example.recipeWeb.domain.Member;
import com.example.recipeWeb.domain.Recipe;
import com.example.recipeWeb.domain.dto.FavoriteDTO;
import com.example.recipeWeb.domain.dto.RecipeDTO;
import com.example.recipeWeb.repository.FavoriteRepository;
import com.example.recipeWeb.repository.MemberRepository;
import com.example.recipeWeb.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteService {
    private final MemberRepository memberRepository;
    private final RecipeRepository recipeRepository;
    private final FavoriteRepository favoriteRepository;

    public void saveFavorite(String username, Long recipeId) {
        Member member = memberRepository.findByUsername(username).orElse(null);
        Recipe recipe = recipeRepository.findById(recipeId).orElse(null);

        Favorite favorite = Favorite.createFavorite(member, recipe);

        favoriteRepository.save(favorite);
    }

    public void deleteFavorite(Long recipeId, String username) {
        Member member = memberRepository.findByUsername(username).get();

        Favorite favorite = favoriteRepository.findByRecipeIdAndMemberId(recipeId, member.getId()).get();

        favoriteRepository.delete(favorite);
    }

    public List<FavoriteDTO> findMyFavorite(String username) {
        List<Favorite> favorites = memberRepository.findByUsername(username).get().getFavorites();
        List<FavoriteDTO> dtos = new ArrayList<>();

        for (Favorite favorite : favorites) {
            Recipe recipe = favorite.getRecipe();
            RecipeDTO recipeDTO = RecipeDTO.generateDTO(recipe);

            dtos.add(new FavoriteDTO(
                    favorite.getId(),
                    recipeDTO,
                    favorite.getDate()
            ));
        }

        return dtos;
    }

    public List<Long> findMyFavoriteId(String username) {
        List<Favorite> favorites = memberRepository.findByUsername(username).get().getFavorites();

        List<Long> ids = new ArrayList<>();

        for(Favorite favorite : favorites) {
            ids.add(favorite.getRecipe().getId());
        }

        return ids;
    }
}
