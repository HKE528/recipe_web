package com.example.recipeWeb.domain;

import com.example.recipeWeb.domain.dto.MemberDTO;
import com.example.recipeWeb.domain.enums.RoleEnum;
import lombok.Getter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@Table(name = "tb_member")
@DynamicInsert
@DynamicUpdate
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(length = 30, nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean enabled = true;

    @ManyToMany
    @JoinTable(
            name = "tb_member_role",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles= new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.ALL)
    private MemberInfo memberInfo;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Recipe> recipes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Favorite> favorites = new ArrayList<>();

    public Member() {
    }

    public Member(String username, String password, Boolean enabled, MemberInfo memberInfo) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.memberInfo = memberInfo;
    }

    //==생성 메서드==//
    public static Member createMember(MemberDTO dto, MemberInfo memberInfo, Role... roles) {
        Member member = new Member(dto.getUsername(), dto.getPassword(), dto.isEnabled(), memberInfo);

        for(Role role : roles) {
            member.getRoles().add(role);
        }

        return member;
    }

    public void changeData(MemberDTO dto) {
        this.password = getPassword();
        this.enabled = dto.isEnabled();
        this.memberInfo.changeData(dto.getNickname(), dto.getEmail());
    }
}
