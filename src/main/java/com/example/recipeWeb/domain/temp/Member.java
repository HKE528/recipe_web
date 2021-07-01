package com.example.recipeWeb.domain.temp;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "tb_member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @Column(name = "member_id", length = 10)
    private String id;

    @Column(nullable = false, length = 20)
    private String pw;

    @Column(name = "member_name", length = 20)
    private String name;

    @Column(length = 50)
    private String email;

    @Column(nullable = false)
    private LocalDate joindate;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private final List<MyRecipes> myRecipes = new ArrayList<>();

    public Member(String id, String pw, String name, String email) {
        this.id = id;
        this.pw = pw;
        this.name = name.isEmpty()? id : name;
        this.email = email;
        joindate = LocalDate.now();
    }

    public Member(MemberDTO memberDTO) {
        this.id = memberDTO.getId();
        this.pw = memberDTO.getPw();
        this.name = memberDTO.getName().isEmpty()? memberDTO.getId() : memberDTO.getName();
        this.email = memberDTO.getEmail();
        joindate = LocalDate.now();
    }

    public void changeInfo(String pw, String name, String email) {
        if(pw != null && !pw.isEmpty()) {
            this.pw = pw;
        }
        this.name = name;
        this.email = email;
    }
}
