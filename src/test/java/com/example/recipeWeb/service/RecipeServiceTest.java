package com.example.recipeWeb.service;

import com.example.recipeWeb.domain.Category;
import com.example.recipeWeb.domain.Recipe;
import com.example.recipeWeb.domain.dto.MemberDTO;
import com.example.recipeWeb.domain.dto.RecipeDTO;
import com.example.recipeWeb.domain.enums.CategoryEnum;
import com.example.recipeWeb.repository.RecipeRepository;
import javassist.bytecode.DuplicateMemberException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class RecipeServiceTest {
    @Autowired RecipeService recipeService;
    @Autowired MemberService memberService;

    String username = "test";

    @Test
    public void 레시피_저장_테스트() throws DuplicateMemberException {
        //given
        MemberDTO member = generateTestDTO(username);
        memberService.joinMember(member);

        RecipeDTO dto = generateDTO();
        dto.setUsername(username);
        
        //when
        Long id = recipeService.saveRecipe(dto);

        //then
        RecipeDTO result = recipeService.findRecipe(id);
        assertEquals(result.getId(), id);
        assertEquals(result.getDate(), dto.getDate());
    }

    @Test(expected = NoSuchElementException.class)
    public void 레시피_삭제_테스트() {
        //given
        RecipeDTO dto = generateDTO();
        Long id = recipeService.saveRecipe(dto);

        //when
        recipeService.deleteRecipe(id);
        recipeService.findRecipe(id);

        //then
        fail("예외 발생 해야함");
    }

    @Test
    public void 레시피_업데이트_테스트() {
        //given
        RecipeDTO dto = generateDTO();
        Long id = recipeService.saveRecipe(dto);

        //when
        String updateName = "Name";
        dto.setName(updateName);

        CategoryEnum updateCategory = CategoryEnum.KOREAN;
        dto.setCategory(updateCategory);

        recipeService.updateRecipe(dto);

        //then
        RecipeDTO result = recipeService.findRecipe(id);
        assertEquals(updateName, result.getName());
        assertEquals(updateCategory, result.getCategory());

    }

    private RecipeDTO generateDTO() {
        return new RecipeDTO(
                "name",
                "ingredient",
                "description",
                "comment",
                CategoryEnum.OTHERS
        );
    }

    private MemberDTO generateTestDTO() {
        MemberDTO memberDTO = new MemberDTO(
                "username",
                "password",
                true,
                "nickname",
                "email@test.com"
        );
        memberDTO.setOtherData(true);

        return memberDTO;
    }

    private MemberDTO generateTestDTO(String username) {
        MemberDTO memberDTO = generateTestDTO();
        memberDTO.setUsername(username);

        return memberDTO;
    }
}