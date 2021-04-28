package com.cs.sigm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cs.sigm.domain.ContentLog;

public interface ContentLogRepository extends JpaRepository<ContentLog, Long> {
	
	public List<ContentLog> findByIdContent(Long idContent);
	
	@Query("delete from ContentLog cl where cl.idContent = :idContent")
	public Integer deleteByIdContent(Long idContent);
	
}
