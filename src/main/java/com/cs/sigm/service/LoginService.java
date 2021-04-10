package com.cs.sigm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.sigm.adapter.domain.LoginDTO;
import com.cs.sigm.domain.User;
import com.cs.sigm.exception.CmsAuthenticationException;
import com.cs.sigm.repository.UserRepository;
import com.cs.sigm.security.auth.AuthUserDetailsService;

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
		final UserDetails userDetails = authUserDetailsService.loadUserByUsername(request.getUsername());
		if (passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
			// TODO: translate the messages
			// user and password OK
			if (!userDetails.isEnabled()) {
				// user has not yet a valid email
				throw new CmsAuthenticationException("Please validate your email before using the portal.");
			}
			return repository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new CmsAuthenticationException("User or password invalid...."));
		} else {
			throw new CmsAuthenticationException("User or password invalid....");
		}
	}
	
}
