package com.cs.sigm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cs.sigm.domain.OperationLog;

public interface OperationLogRepository extends JpaRepository<OperationLog, Long> {

	@Query("SELECT ol FROM OperationLog ol WHERE ol.idEntity = :idEntity AND ol.codeEntity = :codeEntity")
	public List<OperationLog> findLogEntry(Long idEntity, Integer codeEntity);

	@Query("DELETE FROM OperationLog ol WHERE ol.idEntity = :idEntity AND ol.codeEntity = :codeEntity")
	public Integer deleteByIdUser(Long idEntity, Integer codeEntity);

}
