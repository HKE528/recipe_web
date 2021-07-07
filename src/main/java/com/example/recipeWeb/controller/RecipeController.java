package com.example.recipeWeb.controller;

import com.example.recipeWeb.domain.dto.MemberDTO;
import com.example.recipeWeb.domain.dto.RecipeDTO;
import com.example.recipeWeb.domain.enums.CategoryEnum;
import com.example.recipeWeb.domain.enums.OrderTypeEnum;
import com.example.recipeWeb.service.MemberService;
import com.example.recipeWeb.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;
    private final MemberService memberService;

    @GetMapping("/my/{cate}")
    public String myRecipe(@PathVariable("cate") String cate,
                           @RequestParam(value = "searchText", defaultValue = "") String searchText,
                           @RequestParam(value = "orderType", defaultValue = "OLDER") OrderTypeEnum orderType,
                           Principal principal, Model model) {

        String username = principal.getName();
        List<RecipeDTO> myRecipes = recipeService.findAllMyRecipe(username, orderType);

        if (!cate.equals("all")) {
            myRecipes = recipeService.category(cate, myRecipes);
        }

        if (searchText != null && !searchText.isEmpty()) {
            myRecipes = recipeService.search(searchText, myRecipes);
        }

        model.addAttribute("recipes", myRecipes);
        model.addAttribute("selected", cate);
        model.addAttribute("orderType", orderType);

        return "recipe/myRecipe";
    }

    @GetMapping("/my/add")
    public String addRecipe(Model model) {
        model.addAttribute("dto", new RecipeDTO());

        return "recipe/addRecipeForm";
    }

    @PostMapping("/my/add")
    public String addRecipe(@Valid RecipeDTO dto, Principal principal, BindingResult result) {

        if (result.hasErrors()) {

            return "recipe/addRecipeForm";
        }

        String username = principal.getName();
        dto.setUsername(username);

        recipeService.saveRecipe(dto);

        return "redirect:/recipe/my/all";
    }

    @GetMapping("/my/view/{id}")
    public String view(@PathVariable("id") Long id, @RequestParam(value = "selected", defaultValue = "all") String selected,
                       @RequestParam(value = "dest", defaultValue = "my") String dest, Model model) {
        RecipeDTO recipe = recipeService.findRecipe(id);

        model.addAttribute("recipe", recipe);
        model.addAttribute("nlString", System.getProperty("line.separator"));
        model.addAttribute("selected", selected);
        model.addAttribute("dest", dest);

        return "recipe/showMyRecipe";
    }

    @GetMapping("/my/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        RecipeDTO recipe = recipeService.findRecipe(id);

        model.addAttribute("recipe", recipe);

        return "recipe/editRecipeForm";
    }

    @PostMapping("/my/edit/{id}")
    public String edit(@PathVariable("id") Long id, RecipeDTO dto) {
        recipeService.updateRecipe(dto);

        return "redirect:/recipe/my/view/" + id;
    }

    @GetMapping("/my/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        recipeService.deleteRecipe(id);

        return "redirect:/recipe/my/all";
    }

    @GetMapping("/my/shard/{id}")
    public String shard(@PathVariable("id") Long id,
                        @RequestParam("shard") boolean shard,
                        @RequestParam(value = "selected", defaultValue = "all") String cate) {

        recipeService.setShard(id, shard);

//        redirect.addAttribute("cate", cate);

        return "redirect:/recipe/my/" + cate;
    }

    @RequestMapping("/search")
    public String search(@RequestParam("target") String target,
                         @RequestParam(value = "searchText", defaultValue = "") String searchText,
                         RedirectAttributes redirect) {
        String action = "redirect:/";

        action += switch (target) {
            case "home"     -> "home/all";
            case "myrecipe" -> "recipe/my/all";
            default         -> "favorite/all";
        };

        redirect.addAttribute("searchText", searchText);

        return action;
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

        return "redirect:/recipe/my/" + cate;
    }
}
