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
public class LoginDTO implements Serializable {

	private static final long serialVersionUID = -9136852716928512343L;

	@NotEmpty(message = "{validation.login.username}")
	private String username;

	@NotEmpty(message = "{validation.login.password}")
	private String password;

}
