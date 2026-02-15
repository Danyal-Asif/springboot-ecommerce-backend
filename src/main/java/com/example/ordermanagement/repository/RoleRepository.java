package com.example.ordermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ordermanagement.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);
}
