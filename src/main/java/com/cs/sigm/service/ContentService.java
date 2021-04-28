package com.cs.sigm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.sigm.domain.Content;
import com.cs.sigm.domain.ContentLog;
import com.cs.sigm.domain.fixed.Operation;
import com.cs.sigm.exception.CmsEntryNotFoundException;
import com.cs.sigm.repository.ContentLogRepository;
import com.cs.sigm.repository.ContentRepository;
import com.cs.sigm.repository.UserRepository;

@Service
public class ContentService {
	
	@Autowired
	private ContentRepository repository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContentLogRepository logRepository;
	
	public Content findSingle(Long id) {
		return repository.findById(id).orElseThrow(() -> new CmsEntryNotFoundException("User not found."));
	}
	
	public List<Content> findAll() {
		return repository.findAll();
	}
	
	public Content save(Content request, String operatorUsername) {
		Content result = repository.save(request);
		saveLog(Operation.CREATE, operatorUsername, result.getId(), null);
		return result;
	}
	
	public void deleteSingle(Long id, String operatorUsername) {
		if (repository.findById(id).isEmpty()) {
			throw new CmsEntryNotFoundException("The requested entry does not exist, therefore, it was not deleted.");
		}
		repository.deleteById(id);
		saveLog(Operation.REMOVE, operatorUsername, id, null);
	}
	
	private void saveLog(Operation operation, String operatorUsername, Long idContent, String notes) {
		//@formatter:off
		logRepository.save(
			ContentLog.builder()
				.idOperation(operation.getId())
				.idOperator(userRepository.getIdByUsername(operatorUsername))
				.idContent(idContent)
				.notes(notes)
				.build()
		);
		//@formatter:on
	}
	
}
