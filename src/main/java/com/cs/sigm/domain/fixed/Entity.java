package com.cs.sigm.domain.fixed;

import java.util.Arrays;

import com.cs.sigm.exception.CmsEntryNotFoundException;

import lombok.Getter;

@Getter
public enum Entity {

	//@formatter:off
	USER(1, "user"), 
	CONTENT(2, "content");
	//@formatter:on

	private Integer code;

	private String key;

	private Entity(Integer code, String key) {
		this.code = code;
		this.key = key;
	}

	public static String getKeyByCode(Integer code) {
		final Entity result = Arrays.asList(Entity.values()).stream().filter(o -> o.getCode() == code).findFirst()
				.orElseThrow(() -> new CmsEntryNotFoundException("Tried to parse and invalid entity code."));
		return result.getKey();
	}

	public static Entity findByKey(String key) {
		return Arrays.asList(Entity.values()).stream().filter(o -> o.getKey().equals(key)).findFirst()
				.orElseThrow(() -> new CmsEntryNotFoundException("Tried to parse and invalid entity key."));
	}

}
