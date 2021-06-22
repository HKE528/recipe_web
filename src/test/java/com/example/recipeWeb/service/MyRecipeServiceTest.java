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

    private MemberDTO generateMemberDTO() {
        return new MemberDTO(
                "testId", "testPw", "testName", "testEmail"
        );
    }

    private RecipeDTO generateRecipeDTO() {
        return new RecipeDTO("testName", Category.KOREAN, "testtest", "testteststest");
    }
}