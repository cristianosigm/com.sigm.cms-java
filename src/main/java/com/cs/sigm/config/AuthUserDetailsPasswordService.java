package com.cs.sigm.config;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.stereotype.Service;

import com.cs.sigm.domain.User;
import com.cs.sigm.domain.fixed.Role;
import com.cs.sigm.repository.UserRepository;

@Service
@Transactional
public class AuthUserDetailsPasswordService implements UserDetailsPasswordService {

	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails updatePassword(UserDetails user, String newPassword) {
		 final User curUser = repository.findByEmail(user.getUsername()).orElse(null);
		curUser.setPassword(newPassword);
		return new org.springframework.security.core.userdetails.User(curUser.getEmail(), curUser.getPassword(), true, true, true, true,
				getGrantedAuthorities(Role.getKeyById(curUser.getIdRole())));
	}

	private List<GrantedAuthority> getGrantedAuthorities(final String roleName) {
		final List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(roleName));
		return authorities;
	}

}
