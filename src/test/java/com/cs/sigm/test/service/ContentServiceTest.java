package com.cs.sigm.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cs.sigm.domain.Content;
import com.cs.sigm.exception.CmsEntryNotFoundException;
import com.cs.sigm.mapper.ContentMapper;
import com.cs.sigm.service.ContentService;
import com.cs.sigm.test.data.ContentServiceTestSetup;

// @Slf4j
@SpringBootTest
public class ContentServiceTest extends ContentServiceTestSetup {
	
	private static final String OPERATOR_USERNAME = "";
	
	@Autowired
	private ContentService service;
	
	@Autowired
	private ContentMapper mapper;
	
	@Test
	public void when_CreateContentWithEmptyTitle_then_Fail() throws Exception {
		
	}
	
	@Test
	public void when_CreateContentWithEmptyContent_then_Fail() throws Exception {
		
	}
	
	@Test
	public void when_CreateValidContent_then_Success() throws Exception {
		
	}
	
	@Test
	public void when_UpdateContentWithEmptyTitle_then_Fail() throws Exception {
		
	}
	
	@Test
	public void when_UpdateContentWithEmptyContent_then_Fail() throws Exception {
		
	}
	
	@Test
	public void when_UpdateWithValidData_then_Success() throws Exception {
		
	}
	
	@Test
	public void when_findAllWithNoData_then_Success() throws Exception {
		final List<Content> items = service.findAll();
		assertTrue(items != null && items.size() == 0);
	}
	
	@Test
	public void when_findAllWithData_then_Success() throws Exception {
		addSampleData01();
		final List<Content> items = service.findAll();
		assertTrue(items != null && items.size() >= 3);
		removeSampleData(items);
	}
	
	@Test
	public void when_findSingleWithInvalidId_then_Fail() throws Exception {
		Assertions.assertThrows(CmsEntryNotFoundException.class, () -> {
			service.findSingle(Long.MAX_VALUE);
		});
	}
	
	@Test
	public void when_findSingleWithValidId_then_Success() throws Exception {
		final Content item = service.save(mapper.map(contentValid[4]), OPERATOR_USERNAME);
		final Content itemLoaded = service.findSingle(item.getId());
		assertTrue(item.getId().longValue() == itemLoaded.getId().longValue());
	}
	
	@Test
	public void when_deleteWithInvalidId_then_Fail() throws Exception {
		Assertions.assertThrows(CmsEntryNotFoundException.class, () -> {
			service.findSingle(Long.MAX_VALUE);
		});
	}
	
	@Test
	public void when_deleteWithValidId_then_Success() throws Exception {
		final Content saved = service.save(mapper.map(contentValid[7]), OPERATOR_USERNAME);
		final Content found = service.findSingle(saved.getId());
		assertTrue(found != null && found.getId().longValue() == saved.getId().longValue());
		service.deleteSingle(saved.getId(), OPERATOR_USERNAME);
		Assertions.assertThrows(CmsEntryNotFoundException.class, () -> {
			service.findSingle(saved.getId());
		});
	}
	
	private void addSampleData01() {
		final Content result1 = service.save(mapper.map(contentValid[1]), OPERATOR_USERNAME);
		assertEquals(result1.getTitle(), contentValid[1].getTitle());
		final Content result2 = service.save(mapper.map(contentValid[2]), OPERATOR_USERNAME);
		assertEquals(result2.getTitle(), contentValid[2].getTitle());
		final Content result3 = service.save(mapper.map(contentValid[3]), OPERATOR_USERNAME);
		assertEquals(result3.getTitle(), contentValid[3].getTitle());
	}
	
	private void removeSampleData(List<Content> itemsToRemove) {
		for (Content Content : itemsToRemove) {
			service.deleteSingle(Content.getId(), OPERATOR_USERNAME);
		}
		assertTrue(service.findAll().size() == 0);
	}
	
}
