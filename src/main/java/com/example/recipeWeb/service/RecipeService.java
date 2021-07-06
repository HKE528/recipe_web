package com.example.recipeWeb.service;

import com.example.recipeWeb.domain.Member;
import com.example.recipeWeb.domain.Recipe;
import com.example.recipeWeb.domain.RecipeInfo;
import com.example.recipeWeb.domain.dto.RecipeDTO;
import com.example.recipeWeb.domain.enums.CategoryEnum;
import com.example.recipeWeb.domain.enums.OrderTypeEnum;
import com.example.recipeWeb.repository.MemberRepository;
import com.example.recipeWeb.repository.RecipeRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final MemberRepository memberRepository;

    public RecipeDTO findRecipe(Long id) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(NoSuchElementException::new);

        RecipeDTO recipeDTO = RecipeDTO.generateDTO(recipe);
        return recipeDTO;
    }

    public List<RecipeDTO> findAllShardRecipe(String searchText) {
//        List<Recipe> recipes = recipeRepository.findByShareable(true);
        List<Recipe> recipes = recipeRepository.findByShareableAndNameContaining(true, searchText);
        List<RecipeDTO> dtos = new ArrayList<>();

        for(Recipe recipe : recipes) {
            RecipeDTO dto = RecipeDTO.generateDTO(recipe);
            dto.setUsername(recipe.getMember().getUsername());
            dtos.add(dto);
        }

        return dtos;
    }

    public List<RecipeDTO> findAllMyRecipe(String username, OrderTypeEnum orderType) {
        Optional<Member> member = memberRepository.findByUsername(username);
        List<RecipeDTO> recipes = new ArrayList<>();

        if(member.isPresent()) {
            for (Recipe recipe : member.get().getRecipes()) {
                RecipeDTO dto = RecipeDTO.generateDTO(recipe);
                dto.setUsername(recipe.getMember().getUsername());
                recipes.add(dto);
            }
        }

        System.out.println(orderType);

        if(orderType != OrderTypeEnum.OLDER)    recipes = sortByType(recipes, orderType);

        return recipes;
    }

    //생성
    public Long saveRecipe(RecipeDTO dto){
        Member member = memberRepository.findByUsername(dto.getUsername()).orElse(null);
        RecipeInfo recipeInfo = new RecipeInfo();

        Recipe recipe = Recipe.createRecipe(dto, member, recipeInfo);

        Recipe saved = recipeRepository.save(recipe);

        return saved.getId();
    }

    //삭제
    public void deleteRecipe(Long id) {
        Recipe recipe = recipeRepository.findById(id).orElse(null);

        recipeRepository.delete(recipe);
    }

    //업데이트
    public void updateRecipe(RecipeDTO dto) {
        Recipe recipe = recipeRepository.findById(dto.getId()).orElse(null);

        recipe.changeData(dto);
    }

    //분류
    public List<RecipeDTO> category(String cate, List<RecipeDTO> list) {
        CategoryEnum category =
                switch (cate) {
                    case "ko" -> CategoryEnum.KOREAN;
                    case "jp" -> CategoryEnum.JAPANESE;
                    case "ch" -> CategoryEnum.CHINESE;
                    case "we" -> CategoryEnum.WESTERN;
                    default   -> CategoryEnum.OTHERS;
                };

        return list.stream().filter(it -> it.getCategory() == category).toList();
    }

    //검색
    public List<RecipeDTO> search(String text, List<RecipeDTO> list) {
        return list.stream().filter(it -> it.getName().contains(text)).toList();
    }

    //공유
    public void setShard(Long id, boolean shareable) {
        Recipe recipe = recipeRepository.findById(id).get();

        recipe.setShareable(shareable);
    }

    private List<RecipeDTO> sortByType(List<RecipeDTO> list, OrderTypeEnum type) {

        return type == OrderTypeEnum.NAME?
                list.stream().sorted(Comparator.comparing(RecipeDTO::getName)).toList() :
                list.stream().sorted(Comparator.comparing(RecipeDTO::getDate).reversed()).toList();
    }

}
