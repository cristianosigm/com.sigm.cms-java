package com.cs.sigm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.sigm.domain.Post;
import com.cs.sigm.exception.EntryNotFoundException;
import com.cs.sigm.repository.PostRepository;

@Service
public class PostService {
	
	@Autowired
	private PostRepository repository;
	
	public Optional<Post> findSingle(Long id) {
		return repository.findById(id);
	}
	
	public List<Post> findAll() {
		return repository.findAll();
	}
	
	public Post save(Post request) {
		return repository.save(request);
	}
	
	public void deleteSingle(Long id) {
		if (repository.findById(id).isEmpty()) {
			// TODO: replace the message below using translator
			throw new EntryNotFoundException("The requested entry does not exist, therefore, was not deleted.");
		}
		repository.deleteById(id);
	}
	
}
