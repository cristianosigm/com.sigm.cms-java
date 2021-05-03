package com.cs.sigm.test.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cs.sigm.service.OperationLogService;
import com.cs.sigm.test.data.UserLogServiceTestSetup;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class UserLogServiceTest extends UserLogServiceTestSetup {
	
	@Autowired
	private OperationLogService logService;
	
	@Test
	public void when_UserCreated_then_ShouldCreateLog() {
		
	}
	
	@Test
	public void when_UserDeleted_then_ShouldNotDeleteLogs() {
		
	}
	
}
