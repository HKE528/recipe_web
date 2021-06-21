package com.example.recipeWeb.repository;

import com.example.recipeWeb.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(String id) {
        return em.find(Member.class, id);
    }

    public Member findOne(String id, String pw) {
        return em.createQuery("select m from Member m where id = :id and pw = :pw", Member.class)
                .setParameter("id", id)
                .setParameter("pw", pw)
                .getSingleResult();
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findMyName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
