package com.cs.sigm.test.performance;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cs.sigm.security.utils.KeyGenerator;
import com.cs.sigm.test.CmsTestSetup;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class KeyGeneratorPerformanceTest extends CmsTestSetup {
	
	@Autowired
	private KeyGenerator generator;
	
	@Test
	public void shouldGenerateRandomKeyTest() throws Exception {
		int size = 100;
		long start, end;
		start = System.currentTimeMillis();
		for (int i = 1; i < size; i++) {
			log.info(" -> ".concat(generator.getRandomKey()));
		}
		end = System.currentTimeMillis();
		log.warn(
			"\n\n----------------------------------------------------------------------------\n\n :: RESULTS :: time taken to generate {} random keys: {}ms.\n\n----------------------------------------------------------------------------\n\n",
			size, (end - start));
	}
	
}
