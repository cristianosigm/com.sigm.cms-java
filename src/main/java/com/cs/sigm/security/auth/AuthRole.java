/**
 * File created at 4 de jul de 2019 14:55:15 by Cristiano Souza
 */
package com.cs.sigm.security.auth;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRole {

	private Long id;

	private String name;

	private Collection<AuthPrivilege> privileges;

}