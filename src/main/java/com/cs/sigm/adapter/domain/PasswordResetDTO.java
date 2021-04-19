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
public class PasswordResetDTO implements Serializable {
	
	private static final long serialVersionUID = 2725869537945260978L;
	
	private String email;
	
	private String resetKey;
	
	@NotEmpty(message = "{validation.reset.password}")
	private String password;
	
	@NotEmpty(message = "{validation.reset.passwordConfirm}")
	private String passwordConfirm;
	
}
