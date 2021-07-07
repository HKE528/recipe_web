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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/favorite")
public class FavoriteController {
    private final RecipeService recipeService;
    private final FavoriteService favoriteService;

    @GetMapping("/{cate}")
    public String myFavorite(@PathVariable("cate") String cate,
                           @RequestParam(value = "searchText", defaultValue = "") String searchText,
                           @RequestParam(value = "orderType", defaultValue = "OLDER") OrderTypeEnum orderType,
                           Principal principal, Model model) {

        String username = principal.getName();
        //List<FavoriteDTO> myFavorites = favoriteService.findMyFavorite(username);
        List<FavoriteDTO> myFavorites = favoriteService.findMyFavorite(username, orderType);

        if (!cate.equals("all")) {
            myFavorites = favoriteService.category(cate, myFavorites);
        }

        if (searchText != null && !searchText.isEmpty()) {
            myFavorites = favoriteService.search(searchText, myFavorites);
        }

        model.addAttribute("favorites", myFavorites);
        model.addAttribute("selected", cate);
        model.addAttribute("orderType", orderType);

        return "favorite/favorite";
    }

    @RequestMapping("/order")
    public String order(@RequestParam("radio") String radio, @RequestParam("cate") String cate,
                        RedirectAttributes redirect) {

        OrderTypeEnum type = switch (radio) {
            case "name"   -> OrderTypeEnum.NAME;
            case "latest" -> OrderTypeEnum.LATEST;
            default       -> OrderTypeEnum.OLDER;
        };

        redirect.addAttribute("orderType", type);

        return "redirect:/favorite/" + cate;
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable("id") Long id, @RequestParam(value = "selected",
            defaultValue = "all") String selected, RedirectAttributes redirect) {

        redirect.addAttribute("selected", selected);
        redirect.addAttribute("dest", "favorite");

        return "redirect:/recipe/my/view/" + id;
    }
}