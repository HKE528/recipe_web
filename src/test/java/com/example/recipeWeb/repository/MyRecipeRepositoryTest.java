package com.example.recipeWeb.repository;

import com.example.recipeWeb.domain.enums.CategoryEnum;
import com.example.recipeWeb.service.MemberService;
import com.example.recipeWeb.service.MyRecipeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MyRecipeRepositoryTest {

    @Autowired MyRecipeService myRecipeService;
    @Autowired MemberService memberService;


    @Test
    public void 마이레시피_저장_테스트() {
        //given
        MemberDTO memberDTO = new MemberDTO("test", "testpw", "testname", "testemail" );
        String memberId = memberService.join(memberDTO);

        RecipeDTO recipe = new RecipeDTO("tr", CategoryEnum.KOREAN, "asadg", "asdf");

        //when
        int id = myRecipeService.createMyRecipe(memberId, recipe);

        //then
        MyRecipesDTO result = myRecipeService.findMyRecipe(id);
        assertEquals(result.getId(), id);
    }
}