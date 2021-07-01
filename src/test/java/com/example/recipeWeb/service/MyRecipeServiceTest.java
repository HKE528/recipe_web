package com.example.recipeWeb.service;

import com.example.recipeWeb.domain.enums.CategoryEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        RecipeDTO recipe1 = generateRecipeDTO(CategoryEnum.KOREAN);
        RecipeDTO recipe2 = generateRecipeDTO(CategoryEnum.OTHERS);
        RecipeDTO recipe3 = generateRecipeDTO(CategoryEnum.JAPANESE);

        //when
        int ko = myRecipeService.createMyRecipe(memId, recipe1);
        int oh = myRecipeService.createMyRecipe(memId, recipe2);
        int jp = myRecipeService.createMyRecipe(memId, recipe3);

        MyRecipesDTO koDTO1 = myRecipeService.findMyRecipe(ko);
        MyRecipesDTO ohDTO1 = myRecipeService.findMyRecipe(oh);
        MyRecipesDTO jpDTO1 = myRecipeService.findMyRecipe(jp);

        //then
        List<MyRecipesDTO> result1 = myRecipeService.findAllMyRecipeByCategory(memId, CategoryEnum.KOREAN);
        List<MyRecipesDTO> result2 = myRecipeService.findAllMyRecipeByCategory(memId, CategoryEnum.OTHERS);
        List<MyRecipesDTO> result3 = myRecipeService.findAllMyRecipeByCategory(memId, CategoryEnum.JAPANESE);

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
        return new RecipeDTO("testName", CategoryEnum.KOREAN, "testtest", "testteststest");
    }

    private RecipeDTO generateRecipeDTO(CategoryEnum category) {
        return new RecipeDTO("testName", category, "testtest", "testteststest");
    }
}