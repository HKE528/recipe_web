package com.example.recipeWeb.controller;

import com.example.recipeWeb.service.FavoriteService;
import com.example.recipeWeb.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favorite")
public class FavoriteAPI {
    private final FavoriteService favoriteService;

    @PutMapping("/add/{id}")
    public void add(@PathVariable("id") Long recipeId, Principal principal) {
        //System.out.println(principal.getName());

        favoriteService.saveFavorite(principal.getName(), recipeId);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long recipeId, Principal principal) {
        favoriteService.deleteFavorite(recipeId, principal.getName());
    }
}
