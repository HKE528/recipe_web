package com.example.recipeWeb.service;

import com.example.recipeWeb.domain.Member;
import com.example.recipeWeb.domain.Recipe;
import com.example.recipeWeb.domain.RecipeInfo;
import com.example.recipeWeb.domain.dto.RecipeDTO;
import com.example.recipeWeb.domain.enums.CategoryEnum;
import com.example.recipeWeb.domain.enums.OrderTypeEnum;
import com.example.recipeWeb.repository.MemberRepository;
import com.example.recipeWeb.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final MemberRepository memberRepository;
    private final FileService fileService;

    public RecipeDTO findRecipe(Long id) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(NoSuchElementException::new);

        RecipeDTO recipeDTO = RecipeDTO.generateDTO(recipe);
        recipeDTO.setFilename(fileService.findFile(recipeDTO.getUsername(), id));

        return recipeDTO;
    }

    public List<RecipeDTO> findAllShardRecipe(String searchText, OrderTypeEnum type) {
        List<Recipe> recipes = recipeRepository.findByShareableAndNameContaining(true, searchText);
        List<RecipeDTO> dtos = new ArrayList<>();

        for(Recipe recipe : recipes) {
            String username = recipe.getMember().getUsername();

            RecipeDTO dto = RecipeDTO.generateDTO(recipe);
            dto.setUsername(username);

            String filename = fileService.findFile(username, dto.getId());
            dto.setFilename(filename);

            dtos.add(dto);
        }

        if(type != OrderTypeEnum.OLDER)    dtos = sortByType(dtos, type);

        return dtos;
    }

    public List<RecipeDTO> findAllMyRecipe(String username, OrderTypeEnum orderType) {
        Optional<Member> member = memberRepository.findByUsername(username);
        List<RecipeDTO> recipes = new ArrayList<>();

        if(member.isPresent()) {
            for (Recipe recipe : member.get().getRecipes()) {
                RecipeDTO dto = RecipeDTO.generateDTO(recipe);
                dto.setUsername(recipe.getMember().getUsername());

                String filename = fileService.findFile(username, dto.getId());
                dto.setFilename(filename);

                recipes.add(dto);
            }
        }

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

    public Long saveRecipe(RecipeDTO dto, MultipartFile file){
        Long saved = saveRecipe(dto);

        if(file != null && !file.isEmpty())
            fileService.saveFile(dto.getUsername(), saved, file);

        return saved;
    }

    //삭제
    public void deleteRecipe(Long id) {
        Recipe recipe = recipeRepository.findById(id).orElse(null);

        fileService.deleteFile(recipe.getMember().getUsername(), id);

        recipeRepository.delete(recipe);
    }

    //업데이트
    public void updateRecipe(RecipeDTO dto) {
        Recipe recipe = recipeRepository.findById(dto.getId()).orElse(null);

        recipe.changeData(dto);
    }

    public void updateRecipe(RecipeDTO dto, MultipartFile mFile) {
        if(mFile != null) {
            fileService.updateFile(dto.getUsername(), dto.getId(), mFile);

            System.out.println("do update image");
        }

        updateRecipe(dto);
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
                list.stream().sorted(Comparator.comparing(RecipeDTO::getDate)).toList();
    }
}
