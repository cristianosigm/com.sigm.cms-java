package com.cs.sigm.domain.fixed;

import lombok.Getter;

@Getter
public enum Operation {

	SIGNUP(1L, "signup"), 
	VALIDATE(2L, "validate"), 
	RESET(2L, "reset"), 
	CREATE(0L, "create"), 
	UPDATE(2L, "update"),
	REMOVE(0L, "remove"), 
	APPROVE(0L, "approve"), 
	REJECT(0L, "reject");

	private Long id;

	private String key;

	private Operation(Long id, String key) {
		this.id = id;
		this.key = key;
	}

}
