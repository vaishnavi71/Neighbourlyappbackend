package com.blogapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    
}