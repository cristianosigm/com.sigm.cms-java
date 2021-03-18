package com.cs.sigm.config;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.stereotype.Service;

import com.cs.sigm.domain.User;
import com.cs.sigm.domain.fixed.Role;
import com.cs.sigm.repository.UserRepository;
import com.cs.sigm.security.auth.GrantedAuthoritiesGenerator;

@Service
@Transactional
public class AuthUserDetailsPasswordService extends GrantedAuthoritiesGenerator implements UserDetailsPasswordService {

	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails updatePassword(UserDetails user, String newPassword) {
		final User curUser = repository.findByEmail(user.getUsername()).orElse(null);
		curUser.setPassword(newPassword);
		return new org.springframework.security.core.userdetails.User(curUser.getEmail(), curUser.getPassword(), true,
				true, true, true, getGrantedAuthorities(Role.getKeyById(curUser.getIdRole())));
	}

}
