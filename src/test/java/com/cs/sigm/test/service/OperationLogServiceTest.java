package com.cs.sigm.test.service;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.subethamail.wiser.Wiser;

import com.cs.sigm.domain.Content;
import com.cs.sigm.domain.OperationLog;
import com.cs.sigm.domain.User;
import com.cs.sigm.domain.fixed.Entity;
import com.cs.sigm.domain.fixed.Operation;
import com.cs.sigm.mapper.ContentMapper;
import com.cs.sigm.mapper.UserMapper;
import com.cs.sigm.service.ContentService;
import com.cs.sigm.service.OperationLogService;
import com.cs.sigm.service.UserService;
import com.cs.sigm.test.data.OperationLogServiceTestSetup;

//@Slf4j
@SpringBootTest
public class OperationLogServiceTest extends OperationLogServiceTestSetup {

	private static final String OPERATOR_USERNAME = "";

	private static Wiser wiser;

	@Autowired
	private UserService userService;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private ContentService contentService;

	@Autowired
	private ContentMapper contentMapper;

	@Autowired
	private OperationLogService logService;

	@BeforeAll
	public static void setUp() throws Exception {
		wiser = new Wiser(SMTP_PORT);
		wiser.start();
	}

	@AfterAll
	public static void tearDown() throws Exception {
		wiser.stop();
	}

	@Test
	public void when_UserCreated_then_ShouldCreateLog() {
		createUser(0);
	}

	@Test
	public void when_UserDeleted_then_ShouldNotDeleteLogs() {
		final Long userId = createUser(1);
		userService.deleteSingle(userId, OPERATOR_USERNAME);
		final List<OperationLog> logs = logService.findLogEntry(userId, Entity.USER);
		Assertions.assertNotNull(logs);
		Assertions.assertTrue(logs.size() > 0);
	}

	@Test
	public void when_ContentCreated_then_ShouldCreateLogs() {
		createContent(0);
	}

	@Test
	public void when_ContentDeleted_then_ShouldNotDeleteLogs() {
		final Long contentId = createContent(1);
		contentService.deleteSingle(contentId, OPERATOR_USERNAME);
		final List<OperationLog> logs = logService.findLogEntry(contentId, Entity.CONTENT);
		Assertions.assertNotNull(logs);
		Assertions.assertTrue(logs.size() > 0);
	}

	private Long createUser(int index) {
		final User result = userService.save(userMapper.map(users[index]), Operation.CREATE, OPERATOR_USERNAME);
		final List<OperationLog> logs = logService.findLogEntry(result.getId(), Entity.USER);
		Assertions.assertNotNull(logs);
		Assertions.assertTrue(logs.size() > 0);
		return result.getId();
	}

	private Long createContent(int index) {
		final Content result = contentService.save(contentMapper.map(contents[index]), OPERATOR_USERNAME);
		final List<OperationLog> logs = logService.findLogEntry(result.getId(), Entity.CONTENT);
		Assertions.assertNotNull(logs);
		Assertions.assertTrue(logs.size() > 0);
		return result.getId();
	}

}
