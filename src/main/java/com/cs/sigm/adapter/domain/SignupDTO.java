package com.cs.sigm.adapter.domain;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupDTO implements Serializable {
	
	private static final long serialVersionUID = -7322841407061814638L;
	
	private Long id;
	
	@NotEmpty(message = "{validation.user.display_name}")
	private String displayName;
	
	@NotEmpty(message = "{validation.user.email}")
	private String email;
	
	@NotEmpty(message = "{validation.user.name}")
	private String name;
	
	private String password;
	
	private String passwordConfirm;
	
	@NotEmpty(message = "{validation.user.username}")
	private String username;
	
}
