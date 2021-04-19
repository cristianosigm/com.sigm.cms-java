package com.cs.sigm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cs.sigm.adapter.domain.LoginDTO;
import com.cs.sigm.domain.User;
import com.cs.sigm.exception.CmsAccountLockedException;
import com.cs.sigm.exception.CmsAuthenticationException;
import com.cs.sigm.exception.CmsMissingValidationException;
import com.cs.sigm.repository.UserRepository;
import com.cs.sigm.security.auth.AuthUserDetailsService;

@Service
public class LoginService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthUserDetailsService authUserDetailsService;
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private UserService userService;
	
	public User checkLogin(final LoginDTO request) {
		final UserDetails userDetails = authUserDetailsService.loadUserByUsername(request.getUsername());
		if (userDetails.isAccountNonLocked()) {
			if (passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
				// user and password OK
				if (!userDetails.isEnabled()) {
					// user has not yet a valid email
					throw new CmsMissingValidationException("Account not validated.");
				}
				final User validUser = repository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new CmsAuthenticationException());
				if (validUser.getFailedAttempts().intValue() > 0) {
					repository.resetFailedLoginAttempts(request.getUsername());
				}
				return validUser;
			} else {
				// invalid credentials
				userService.checkAccountLock(request.getUsername());
				throw new CmsAuthenticationException();
			}
		} else {
			throw new CmsAccountLockedException("Please reset your account to proceed.");
		}
	}
	
}
