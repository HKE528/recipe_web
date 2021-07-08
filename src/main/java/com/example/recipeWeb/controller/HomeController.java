package com.example.recipeWeb.controller;

import com.example.recipeWeb.domain.Role;
import com.example.recipeWeb.domain.dto.RecipeDTO;
import com.example.recipeWeb.domain.enums.OrderTypeEnum;
import com.example.recipeWeb.domain.enums.RoleEnum;
import com.example.recipeWeb.repository.RoleRepository;
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
public class HomeController {
    private final RecipeService recipeService;
    private final FavoriteService favoriteService;

    @RequestMapping
    public String home() {
        return "redirect:/home/all";
    }

    @RequestMapping("/home/{cate}")
    public String home(@PathVariable("cate")String cate, Model model,
                       @RequestParam(value = "searchText", defaultValue = "") String searchText,
                       @RequestParam(value = "radio", defaultValue = "") String radio, Principal principal) {

        OrderTypeEnum orderType = switch (radio) {
            case "name"   -> OrderTypeEnum.NAME;
            case "latest" -> OrderTypeEnum.LATEST;
            default       -> OrderTypeEnum.OLDER;
        };

        List<RecipeDTO> recipes = recipeService.findAllShardRecipe(searchText, orderType);

        if(!cate.equals("all")) {
            recipes = recipeService.category(cate, recipes);
        }

        if(principal != null) {
            List<Long> myFavoriteId = favoriteService.findMyFavoriteId(principal.getName());

            recipes.forEach(
                    it -> {
                        //if(myFavoriteId.contains(it.getId())) it.setFavorite(true);
                        for(Long id: myFavoriteId) {
                            if(it.getId().equals(id))    it.setFavorite(true);
                        }
                    });
        }

        model.addAttribute("recipes", recipes);
        model.addAttribute("selected", cate);
        model.addAttribute("orderType", orderType);

        return "home";
    }

    @GetMapping("/home/view/{id}")
    public String view(@PathVariable("id") Long id, @RequestParam(value = "selected",
            defaultValue = "all") String selected, RedirectAttributes redirect) {

        redirect.addAttribute("selected", selected);
        redirect.addAttribute("dest", "home");

        return "redirect:/recipe/my/view/" + id;
    }
}
