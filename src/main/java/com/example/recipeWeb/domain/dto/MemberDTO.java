package com.example.recipeWeb.domain.dto;

import com.example.recipeWeb.domain.enums.RoleEnum;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class MemberDTO {

    private Long id;
    private String username;
    private String password;
    private boolean enabled;

    private String nickname;
    private String email;
    private LocalDate joinDate;

    public void setOtherData(boolean enabled) {
        this.enabled = enabled;
        this.joinDate = LocalDate.now();

        if(this.nickname == null) {
            this.nickname = this.username;
        }
    }

    public MemberDTO() {
    }

    public MemberDTO(Long id, String username, String password, boolean enabled, String nickname, String email, LocalDate joinDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.nickname = nickname;
        this.email = email;
        this.joinDate = joinDate;
    }
}
