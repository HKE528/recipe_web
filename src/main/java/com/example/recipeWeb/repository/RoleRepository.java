package com.example.recipeWeb.repository;

import com.example.recipeWeb.domain.Role;
import com.example.recipeWeb.domain.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleEnum name);
}
