package com.cs.sigm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cs.sigm.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("select u.name from User u where u.id=:id")
	public String getNameById(Long id);

	public Optional<User> findByEmail(String email);
}
