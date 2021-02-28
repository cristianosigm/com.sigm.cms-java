package com.cs.sigm.domain.fixed;

import lombok.Getter;

@Getter
public enum Operation {
	
	CREATE(1L, "create"),
	VALIDATE(2L, "validate"),
	SIGNUP(3L, "signup"),
	UPDATE(4L, "update"),
	APPROVE(5L, "approve"),
	RESET(6L, "reset"),
	REMOVE(7L, "remove"),
	REJECT(8L, "reject");
	
	private Long id;
	
	private String key;
	
	private Operation(Long id, String key) {
		this.id = id;
		this.key = key;
	}
	
}
