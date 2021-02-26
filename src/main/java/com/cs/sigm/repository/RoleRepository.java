package com.cs.sigm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cs.sigm.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
