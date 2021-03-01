package com.cs.sigm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.sigm.domain.UserLog;
import com.cs.sigm.repository.UserLogRepository;

@Service
public class UserLogService {

	@Autowired
	private UserLogRepository repository;

	public List<UserLog> findAllByUserId(Long idUser) {
		return repository.findByIdUser(idUser);
	}

	public UserLog save(UserLog request) {
		return repository.save(request);
	}

}
