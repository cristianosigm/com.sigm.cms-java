package com.cs.sigm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;
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
	@Index(name = "idx_user_username", columnList = "username", unique = true), @Index(name = "idx_user_email", columnList = "email", unique = true)
})
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name = "id_role")
	private Long idRole;
	
//	@Builder.Default
//	private Boolean approved = false;
	
	@Builder.Default
	private Boolean blocked = false;
	
	@Builder.Default
	private Boolean validated = false;
	
	@NotEmpty
	@Column(name = "display_name")
	private String displayName;
	
	@NotEmpty
	private String email;
	
	@Builder.Default
	@Column(name = "failed_attempts")
	private Integer failedAttempts = 0;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	private String password;
	
	private String passwordResetKey;
	
	@NotEmpty
	private String username;
	
	@NotEmpty
	private String validationKey;
	
	@Transient
	private Boolean changePassword;
	
	@Transient
	private String currentPassword;
	
	@Transient
	private String passwordConfirm;
	
	public void increaseFailedAttepts() {
		this.failedAttempts = Integer.valueOf(this.failedAttempts.intValue() + 1);
	}
	
}
