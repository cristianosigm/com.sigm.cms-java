package com.cs.sigm.domain.fixed;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum Operation {
	
	CREATE(1L, "create"),
	VALIDATE(2L, "validate"),
	SIGNUP(3L, "signup"),
	UPDATE(4L, "update"),
	CHANGEPASSWORD(5L, "change password"),
//	APPROVE(6L, "approve"),
	RESET(7L, "reset"),
	REMOVE(8L, "remove"),
	REJECT(9L, "reject");
	
	private Long id;
	
	private String key;
	
	private Operation(Long id, String key) {
		this.id = id;
		this.key = key;
	}
	
	public static String getKeyById(Long id) {
		final Operation result = Arrays.asList(Operation.values()).stream().filter(o -> o.getId() == id).findFirst().orElse(null); 
		return (result != null ? result.getKey() : "");
	}
	
}
