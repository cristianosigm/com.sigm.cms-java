package com.cs.sigm.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.sigm.domain.fixed.Role;
import com.cs.sigm.exception.CmsAuthenticationException;
import com.cs.sigm.repository.UserRepository;

@Service("userDetailsService")
@Transactional
public class AuthUserDetailsService extends GrantedAuthoritiesGenerator implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(final String email) {
		final com.cs.sigm.domain.User user = userRepository.findByEmail(email).orElseThrow(() -> new CmsAuthenticationException());
		return new User(user.getEmail(), user.getPassword(), user.getValidated(), true, true, user.getBlocked(), getGrantedAuthorities(Role.getKeyById(user.getIdRole())));
	}
	
}
