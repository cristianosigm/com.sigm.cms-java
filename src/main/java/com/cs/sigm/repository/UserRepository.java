package com.cs.sigm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cs.sigm.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
}
