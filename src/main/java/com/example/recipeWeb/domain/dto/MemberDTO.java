package com.example.recipeWeb.domain.dto;

import com.example.recipeWeb.domain.enums.RoleEnum;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberDTO {

    private Long id;
    private String username;
    private String password;
    private boolean enabled;
    
    private RoleEnum role;

    private String nickname;
    private String email;
    private LocalDate joinDate;
}
