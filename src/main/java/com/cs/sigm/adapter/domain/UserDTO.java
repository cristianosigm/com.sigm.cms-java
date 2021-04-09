package com.cs.sigm.adapter.domain;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
public class UserDTO implements Serializable {
	
	private static final long serialVersionUID = 2810036066202240604L;
	
	private Long id;
	
	@NotNull(message = "{validation.user.id_role}")
	private Long idRole;
	
	private Boolean approved;
	
	private Boolean blocked;
	
	@NotEmpty(message = "{validation.user.display_name}")
	private String displayName;
	
	@NotEmpty(message = "{validation.user.email}")
	private String email;
	
	private Integer failedAttempts;
	
	@NotEmpty(message = "{validation.user.name}")
	private String name;
	
	private String password;
	
	private String roleName;
	
	private String username;
	
	private Boolean validated;
	
	private Boolean changePassword;
	
	private String currentPassword;
	
	private String passwordConfirm;
	
}
