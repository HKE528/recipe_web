package com.example.recipeWeb.service;

import com.example.recipeWeb.domain.enums.CategoryEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RecipeServiceTest {
    @Autowired RecipeService recipeService;

    @Test
    public void 레시피_저장_테스트() {
        //given
        RecipeDTO dto = generateDTO();

        //when
        int id = recipeService.saveRecipe(dto);

        //then
        RecipeDTO result = recipeService.loadRecipe(id);
        assertEquals(dto.getName(), result.getName());

    }

    @Test
    public void 레시피_수정_테스트() {
        //given
        RecipeDTO dto = generateDTO();
        String chName = "changName";

        //when
        int id = recipeService.saveRecipe(dto);
        RecipeDTO find = recipeService.loadRecipe(id);

        find.setName(chName);
        recipeService.updateRecipe(find);

        //then
        RecipeDTO result = recipeService.loadRecipe(id);
        assertEquals(chName, result.getName());
    }

    private RecipeDTO generateDTO() {
        return new RecipeDTO("testName", CategoryEnum.KOREAN, "testtest", "testteststest");
    }

    private RecipeDTO generateDTO(String name) {
        return new RecipeDTO(name, CategoryEnum.KOREAN, "testtest", "testteststest");
    }
}