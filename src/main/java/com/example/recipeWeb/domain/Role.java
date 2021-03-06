package com.example.recipeWeb.domain;

import com.example.recipeWeb.domain.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@Table(name = "tb_role")
public class Role {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long Id;

    @Column(name = "name", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    @ManyToMany(mappedBy = "roles")
    List<Member> members = new ArrayList<>();

    public Role() {
    }

    public Role(RoleEnum name) {
        this.name = name;
    }

    public static Role createUserRole() {
        return new Role(RoleEnum.ROLE_USER);
    }

    public static Role createAdminRole() {
        return new Role(RoleEnum.ROLE_ADMIN);
    }
}
