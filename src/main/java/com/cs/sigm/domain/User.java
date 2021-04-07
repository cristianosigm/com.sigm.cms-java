package com.cs.sigm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tbl_user", indexes = {
	@Index(name = "idx_user_username", columnList = "username", unique = true), 
	@Index(name = "idx_user_email", columnList = "email", unique = true)
})
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name = "id_role")
	private Long idRole;
	
	private Boolean approved;
	
	private Boolean blocked;
	
	private Boolean validated;
	
	@NotEmpty
	@Column(name = "display_name")
	private String displayName;
	
	@NotEmpty
	private String email;
	
	@Column(name = "failed_attempts")
	private Integer failedAttempts;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	private String password;
	
	@NotEmpty
	private String username;
	
	@NotEmpty
	private String validationKey;
	
}
