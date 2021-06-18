package com.example.recipeWeb.service;

import com.example.recipeWeb.DTO.RecipeDTO;
import com.example.recipeWeb.domain.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

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

    private RecipeDTO generateDTO() {
        return new RecipeDTO("testName", Category.KOREAN, "testtest", "testteststest");
    }
}