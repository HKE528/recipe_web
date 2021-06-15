package com.example.recipeWeb.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;
import java.util.List;

@Entity
@Getter
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
    private Date joindate;
    
    private List<MyRecipes> myRecipes;

    public Member(String id, String pw, String name, String email) {
        this.id = id;
        this.pw = pw;
        this.name = name.isEmpty()? id : name;
        this.email = email;
        this.joindate = new Date((new java.util.Date()).getTime());
    }
}
