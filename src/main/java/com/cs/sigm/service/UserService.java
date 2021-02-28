package com.cs.sigm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.sigm.domain.User;
import com.cs.sigm.domain.UserLog;
import com.cs.sigm.domain.fixed.Operation;
import com.cs.sigm.exception.EntryNotFoundException;
import com.cs.sigm.repository.UserLogRepository;
import com.cs.sigm.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private UserLogRepository logRepository;
	
	public Optional<User> findSingle(Long id) {
		return repository.findById(id);
	}
	
	public List<User> findAll() {
		return repository.findAll();
	}
	
	public User save(User request, Operation operation, Long idOperator) {
		final User result = repository.save(request);
		logRepository.save(UserLog.builder().idOperation(operation.getId()).idOperator(idOperator).idUser(result.getId()).build());
		return result;
	}
	
	public void deleteSingle(Long id) {
		if (repository.findById(id).isEmpty()) {
			// TODO: replace the message below using translator
			throw new EntryNotFoundException("The requested entry does not exist, therefore, was not deleted.");
		}
		repository.deleteById(id);
	}
	
}
