package com.cs.sigm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.cs.sigm.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("select u.name from User u where u.id = :idUser")
	public String getNameById(final Long idUser);
	
	public Optional<User> findByEmail(final String email);
	
	@Query("select u.failedAttempts from User u where u.username = :username")
	public int countFailedLoginAttempts(String username);
	
	@Transactional
	@Modifying
	@Query("update User u set u.failedAttempts = (u.failedAttempts + 1) where u.username = :username")
	public void increaseFailedLoginAttempts(String username);

	@Transactional
	@Modifying
	@Query("update User u set u.blocked = true where u.username = :username")
	public void lockUser(String username);
	

	@Transactional
	@Modifying
	@Query("update User u set u.failedAttempts = 0 where u.username = :username")
	public void resetFailedLoginAttempts(String username);
	
}
