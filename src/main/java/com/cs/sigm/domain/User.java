package com.cs.sigm.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

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
@Table(name = "tbl_user")
public class User implements Serializable {

	private static final long serialVersionUID = 8972684400155836411L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "id_role")
	private Long idRole;

	private Boolean approved;

	private Boolean blocked;

	@Column(name = "date_approval")
	private Date dateApproval;

	@Column(name = "date_blocked")
	private Date dateBlocked;

	@Column(name = "date_last_login")
	private Date dateLastLogin;

	@Column(name = "date_validation")
	private Date dateValidation;

	@NotNull
	@Column(name = "display_name")
	private String displayName;

	@NotNull
	private String email;

	@Column(name = "failed_attempts")
	private Integer failedAttempts;

	@NotNull
	private String name;

	@NotNull
	private String password;

	@NotNull
	private String username;

	private Boolean validated;

}
