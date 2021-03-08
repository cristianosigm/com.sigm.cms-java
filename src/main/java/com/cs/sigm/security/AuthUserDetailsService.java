package com.cs.sigm.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.sigm.domain.fixed.Role;
import com.cs.sigm.exception.AuthenticationException;
import com.cs.sigm.repository.UserRepository;

@Service("userDetailsService")
@Transactional
public class AuthUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	private List<GrantedAuthority> getGrantedAuthorities(final String roleName) {
		final List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(roleName));
		return authorities;
	}

	@Override
	public UserDetails loadUserByUsername(final String email) {
		final com.cs.sigm.domain.User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new AuthenticationException("Usuário ou senha inválidos."));

		return new User(user.getEmail(), user.getPassword(), true, true, true, true,
				getGrantedAuthorities(Role.getKeyById(user.getIdRole())));
	}

}