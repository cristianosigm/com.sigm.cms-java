package com.cs.sigm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.sigm.domain.Content;
import com.cs.sigm.exception.CmsEntryNotFoundException;
import com.cs.sigm.repository.ContentRepository;

@Service
public class ContentService {

	@Autowired
	private ContentRepository repository;

	public Optional<Content> findSingle(Long id) {
		return repository.findById(id);
	}

	public List<Content> findAll() {
		return repository.findAll();
	}

	public Content save(Content request) {
		return repository.save(request);
	}

	public void deleteSingle(Long id) {
		if (repository.findById(id).isEmpty()) {
			// TODO: replace the message below using translator
			throw new CmsEntryNotFoundException("The requested entry does not exist, therefore, was not deleted.");
		}
		repository.deleteById(id);
	}

}
