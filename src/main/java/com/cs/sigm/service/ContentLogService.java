package com.cs.sigm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.sigm.domain.ContentLog;
import com.cs.sigm.repository.ContentLogRepository;

@Service
public class ContentLogService {
	
	@Autowired
	private ContentLogRepository repository;
	
	public List<ContentLog> findAllByIdContent(Long idContent) {
		return repository.findByIdContent(idContent);
	}
	
	public ContentLog save(ContentLog request) {
		return repository.save(request);
	}
	
}
