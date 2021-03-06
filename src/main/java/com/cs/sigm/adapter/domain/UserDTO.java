package com.cs.sigm.adapter.domain;

import java.io.Serializable;
import java.util.Date;

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

	private Date dateApproval;

	private Date dateBlocked;

	private Date dateLastLogin;

	private Date dateValidation;

	@NotEmpty(message = "{validation.user.display_name}")
	private String displayName;

	@NotEmpty(message = "{validation.user.email}")
	private String email;

	private Integer failedAttempts;

	@NotEmpty(message = "{validation.user.name}")
	private String name;

	@NotEmpty(message = "{validation.user.password}")
	private String password;

	@NotEmpty(message = "{validation.user.username}")
	private String username;

	private Boolean validated;

}
