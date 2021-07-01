package com.example.recipeWeb.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity @Getter
@Table(name = "tb_memberinfo")
public class MemberInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberinfo_id")
    private Long id;

    @Column(length = 30)
    private String nike;

    @Column(length = 50)
    private String email;

    @Column(nullable = false)
    private LocalDate joindate;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    Member member;
}
