package com.cs.sigm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.sigm.adapter.domain.LoginDTO;
import com.cs.sigm.domain.User;
import com.cs.sigm.exception.AuthenticationException;
import com.cs.sigm.repository.UserRepository;
import com.cs.sigm.security.AuthUserDetailsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class LoginService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthUserDetailsService authUserDetailsService;

	@Autowired
	private UserRepository repository;

	public User checkLogin(final LoginDTO request) {
		final UserDetails user = authUserDetailsService.loadUserByUsername(request.getUsername());
		// TODO: translate the messages
		if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			// user and password OK
			return repository.findByEmail(user.getUsername())
					.orElseThrow(() -> new AuthenticationException("User or password invalid...."));
		} else {
			throw new AuthenticationException("User or password invalid....");
		}
	}

}
