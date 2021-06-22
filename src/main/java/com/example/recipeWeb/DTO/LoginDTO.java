package com.example.recipeWeb.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LoginDTO {

    private String id;
    private String pw;
}
