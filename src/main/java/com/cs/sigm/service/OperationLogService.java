package com.cs.sigm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.sigm.domain.OperationLog;
import com.cs.sigm.domain.fixed.Entity;
import com.cs.sigm.domain.fixed.Operation;
import com.cs.sigm.repository.OperationLogRepository;

@Service
public class OperationLogService {

	@Autowired
	private OperationLogRepository repository;

	public List<OperationLog> findLogEntry(Long idEntity, Entity entity) {
		return repository.findLogEntry(idEntity, entity.getCode());
	}

	public OperationLog save(Entity entity, Operation operation, Long idOperator, Long idEntity, String notes) {
		//@formatter:off
		return repository.save(OperationLog.builder()
				.codeEntity(entity.getCode())
				.codeOperation(operation.getCode())
				.idEntity(idEntity)
				.idOperator(idOperator)
				.notes(notes)
				.build());
		//@formatter:on
	}

}
