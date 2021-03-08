/**
 * File created at 4 de jul de 2019 14:34:56 by Cristiano Souza
 */
package com.cs.sigm.security;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthUser {

	private Long id;

	private String firstName;

	private String lastName;

	private String email;

	private String password;

	private boolean enabled;

	private boolean tokenExpired;

	private Collection<AuthRole> roles;

}
