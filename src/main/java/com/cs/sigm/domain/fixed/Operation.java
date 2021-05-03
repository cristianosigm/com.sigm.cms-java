package com.cs.sigm.domain.fixed;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum Operation {

	//@formatter:off
	CREATE(1, "create"),
	VALIDATE(2, "validate"),
	SIGNUP(3, "signup"),
	UPDATE(4, "update"),
	CHANGEPASSWORD(5, "changep"),
	RESET(6, "reset"),
	REMOVE(7, "remove"),
	REJECT(8, "reject"),
	LOCKED(9, "locked");
	//@formatter:on

	private Integer code;

	private String key;

	private Operation(Integer code, String key) {
		this.code = code;
		this.key = key;
	}

	public static String getKeyByCode(Integer code) {
		final Operation result = Arrays.asList(Operation.values()).stream().filter(o -> o.getCode() == code).findFirst()
				.orElse(null);
		return (result != null ? result.getKey() : "");
	}

}
