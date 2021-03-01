package com.cs.sigm.domain.fixed;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum Role {

	STANDARD(1L, "standard"), ADMINISTRATOR(2L, "administrator");

	private Long id;

	private String key;

	private Role(Long id, String key) {
		this.id = id;
		this.key = key;
	}

	public static String getKeyById(Long id) {
		final Role result = Arrays.asList(Role.values()).stream().filter(o -> o.getId() == id).findFirst().orElse(null);
		return (result != null ? result.getKey() : "");
	}

}
