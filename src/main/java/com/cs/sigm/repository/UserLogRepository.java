package com.cs.sigm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cs.sigm.domain.UserLog;

public interface UserLogRepository extends JpaRepository<UserLog, Long> {
	
	public List<UserLog> findByIdUser(Long idUser);
	
	@Query("delete from UserLog ul where ul.idUser=:idUser")
	public Integer deleteByIdUser(Long idUser);
	
}
