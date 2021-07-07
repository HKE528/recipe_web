package com.example.recipeWeb.service;

import com.example.recipeWeb.domain.Favorite;
import com.example.recipeWeb.domain.Member;
import com.example.recipeWeb.domain.Recipe;
import com.example.recipeWeb.domain.dto.FavoriteDTO;
import com.example.recipeWeb.domain.dto.RecipeDTO;
import com.example.recipeWeb.domain.enums.CategoryEnum;
import com.example.recipeWeb.domain.enums.OrderTypeEnum;
import com.example.recipeWeb.repository.FavoriteRepository;
import com.example.recipeWeb.repository.MemberRepository;
import com.example.recipeWeb.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
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

    public List<FavoriteDTO> findMyFavorite(String username, OrderTypeEnum type) {
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

        if(type != OrderTypeEnum.OLDER)    dtos = sortByType(dtos, type);

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

    public List<FavoriteDTO> search(String text, List<FavoriteDTO> list) {
        return list.stream().filter(it -> it.getRecipe().getName().contains(text)).toList();
    }

    public List<FavoriteDTO> category(String cate, List<FavoriteDTO> myFavorites) {
        CategoryEnum category =
                switch (cate) {
                    case "ko" -> CategoryEnum.KOREAN;
                    case "jp" -> CategoryEnum.JAPANESE;
                    case "ch" -> CategoryEnum.CHINESE;
                    case "we" -> CategoryEnum.WESTERN;
                    default   -> CategoryEnum.OTHERS;
                };

        return myFavorites.stream().filter(it -> it.getRecipe().getCategory() == category).toList();
    }

    private List<FavoriteDTO> sortByType(List<FavoriteDTO> list, OrderTypeEnum type) {

        return type == OrderTypeEnum.NAME?
                list.stream().sorted(Comparator.comparing(FavoriteDTO::getRecipe,
                        Comparator.comparing(RecipeDTO::getName))).toList() :
                list.stream().sorted(Comparator.comparing(FavoriteDTO::getDate)).toList();
    }
}
