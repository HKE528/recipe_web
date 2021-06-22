package com.example.recipeWeb.DTO;

import com.example.recipeWeb.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {

    private String id;
    private String pw;
    private String name;
    private String email;

    public MemberDTO(String id) {
        this.id = id;
    }

    public MemberDTO() {

    }

    public MemberDTO(String id, String pw, String name, String email) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.email = email;
    }

    public static MemberDTO createDTO(Member member) {
        return new MemberDTO(
                member.getId(),
                member.getPw(),
                member.getName(),
                member.getEmail()
        );
    }
}
