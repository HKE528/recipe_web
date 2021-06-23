package com.example.recipeWeb.service;

import com.example.recipeWeb.DTO.MemberDTO;
import com.example.recipeWeb.DTO.MyRecipesDTO;
import com.example.recipeWeb.DTO.RecipeDTO;
import com.example.recipeWeb.domain.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MyRecipeServiceTest {
    @Autowired private RecipeService recipeService;
    @Autowired private MemberService memberService;
    @Autowired private MyRecipeService myRecipeService;

    @Test
    public void 레시피_불러오기_테스트() {
        //given
        MemberDTO mem1 = generateMemberDTO();
        String memId = memberService.join(mem1);

        RecipeDTO recipe1 = generateRecipeDTO();
        //int recipeId = recipeService.saveRecipe(recipe1);

        //when
        int myRecipeId = myRecipeService.createMyRecipe(memId, recipe1);
        MyRecipesDTO result1 = myRecipeService.findMyRecipe(myRecipeId);
        List<MyRecipesDTO> resultList = myRecipeService.findAllMyRecipe(memId);

        //then
        assertEquals(result1.getId(), resultList.get(0).getId());

    }

    @Test
    public void 레시피_카테고리별_로딩_테스트() {
        //given
        MemberDTO mem1 = generateMemberDTO();
        String memId = memberService.join(mem1);

        RecipeDTO recipe1 = generateRecipeDTO(Category.KOREAN);
        RecipeDTO recipe2 = generateRecipeDTO(Category.OTHERS);
        RecipeDTO recipe3 = generateRecipeDTO(Category.JAPANESE);

        //when
        int ko = myRecipeService.createMyRecipe(memId, recipe1);
        int oh = myRecipeService.createMyRecipe(memId, recipe2);
        int jp = myRecipeService.createMyRecipe(memId, recipe3);

        MyRecipesDTO koDTO1 = myRecipeService.findMyRecipe(ko);
        MyRecipesDTO ohDTO1 = myRecipeService.findMyRecipe(oh);
        MyRecipesDTO jpDTO1 = myRecipeService.findMyRecipe(jp);

        //then
        List<MyRecipesDTO> result1 = myRecipeService.findAllMyRecipeByCategory(memId, Category.KOREAN);
        List<MyRecipesDTO> result2 = myRecipeService.findAllMyRecipeByCategory(memId, Category.OTHERS);
        List<MyRecipesDTO> result3 = myRecipeService.findAllMyRecipeByCategory(memId, Category.JAPANESE);

        assertEquals(koDTO1.getId(), result1.get(0).getId());
        assertEquals(ohDTO1.getId(), result2.get(0).getId());
        assertEquals(jpDTO1.getId(), result3.get(0).getId());
    }

    private MemberDTO generateMemberDTO() {
        return new MemberDTO(
                "testId", "testPw", "testName", "testEmail"
        );
    }

    private RecipeDTO generateRecipeDTO() {
        return new RecipeDTO("testName", Category.KOREAN, "testtest", "testteststest");
    }

    private RecipeDTO generateRecipeDTO(Category category) {
        return new RecipeDTO("testName", category, "testtest", "testteststest");
    }
}