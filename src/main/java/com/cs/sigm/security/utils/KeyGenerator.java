package com.cs.sigm.security.utils;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.sigm.config.CmsConfig;

@Service
public class KeyGenerator {

	@Autowired
	private CmsConfig config;

	private static int CHAR_ZERO = 48;

	private static int CHAR_Z = 122;

	public String getRandomKey() {
		return (new Random()).ints(CHAR_ZERO, CHAR_Z + 1).filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
				.limit(config.getKeySize())
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
	}

}
