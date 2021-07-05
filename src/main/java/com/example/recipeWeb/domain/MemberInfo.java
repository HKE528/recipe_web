package com.example.recipeWeb.domain;

import com.example.recipeWeb.domain.dto.MemberDTO;
import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity @Getter
@Table(name = "tb_memberinfo")
@DynamicUpdate
public class MemberInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberinfo_id")
    private Long id;

    @Column(length = 30)
    private String nickname;

    @Column(length = 50)
    private String email;

    @Column(nullable = false)
    private LocalDate joindate;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    Member member;

    public MemberInfo() {
    }

    public MemberInfo(String nickname, String email, LocalDate joindate) {
        this.nickname = nickname;
        this.email = email;
        this.joindate = joindate;
    }

    public static MemberInfo createMemberInfo(MemberDTO dto) {
        dto.setJoinDate(LocalDate.now());

        MemberInfo memberInfo = new MemberInfo(
                dto.getNickname(),
                dto.getEmail(),
                dto.getJoinDate()
        );

        return memberInfo;
    }

    public void changeData(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }
}
