package com.example.recipeWeb.controller;

import com.example.recipeWeb.domain.dto.FavoriteDTO;
import com.example.recipeWeb.domain.dto.RecipeDTO;
import com.example.recipeWeb.domain.enums.OrderTypeEnum;
import com.example.recipeWeb.service.FavoriteService;
import com.example.recipeWeb.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/favorite")
public class FavoriteController {
    private final RecipeService recipeService;
    private final FavoriteService favoriteService;

    @GetMapping("/my/{cate}")
    public String myRecipe(@PathVariable("cate") String cate,
                           @RequestParam(value = "searchText", defaultValue = "") String searchText,
                           @RequestParam(value = "orderType", defaultValue = "OLDER") OrderTypeEnum orderType,
                           Principal principal, Model model) {

        String username = principal.getName();
        List<FavoriteDTO> myFavorites = favoriteService.findMyFavorite(username);

//        if (!cate.equals("all")) {
//            myRecipes = recipeService.category(cate, myRecipes);
//        }
//
//        if (searchText != null && !searchText.isEmpty()) {
//            myRecipes = recipeService.search(searchText, myRecipes);
//        }

        model.addAttribute("favorites", myFavorites);
        model.addAttribute("selected", cate);
        model.addAttribute("orderType", orderType);

        return "favorite/myFavorite";
    }
}
