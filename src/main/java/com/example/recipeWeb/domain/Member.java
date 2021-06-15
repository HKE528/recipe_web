package com.example.recipeWeb.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
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
    private List<MyRecipes> myRecipes;

    public Member(String id, String pw, String name, String email) {
        this.id = id;
        this.pw = pw;
        this.name = name.isEmpty()? id : name;
        this.email = email;
    }
}
