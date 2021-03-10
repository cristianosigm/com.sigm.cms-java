package com.cs.sigm.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class GrantedAuthoritiesGenerator {

	protected List<GrantedAuthority> getGrantedAuthorities(final String roleName) {
		final List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(roleName));
		log.info(" -- adding new Role to current user: " + roleName);
		return authorities;
	}

}
