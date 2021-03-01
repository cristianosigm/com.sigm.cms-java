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
public class RoleDTO implements Serializable {

	private static final long serialVersionUID = 7531807705318187551L;

	private Long id;

	@NotEmpty(message = "{validation.role.title}")
	private String title;

	private String description;

}