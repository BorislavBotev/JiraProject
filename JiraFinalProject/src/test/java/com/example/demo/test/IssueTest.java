package com.example.demo.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.JiraFinalProjectApplicationTests;
import com.example.demo.dao.IssueDao;
import com.example.demo.dto.AddIssueDTO;
import com.example.demo.dto.EditIssueDTO;
import com.example.demo.exceptions.IssueException;
import com.example.demo.exceptions.PriorityException;
import com.example.demo.exceptions.StatusException;
import com.example.demo.exceptions.TypeException;
import com.example.demo.model.Issue;
import com.example.demo.repositories.IssueRepository;
import com.example.demo.service.IssueService;

public class IssueTest extends JiraFinalProjectApplicationTests {
	private static final int PRIORITIES_COUNT = 5;
	private static final int STATUSES_COUNT = 3;
	private static final int TYPES_COUNT = 4;
	private static final long TEST_ISSUE_ID = 10;
	
	@Autowired
	private IssueRepository issueRepository;
	@Autowired
	private IssueDao issueDao;
	@Autowired
	private IssueService issueService;
	
	public AddIssueDTO createTestIssue() {
		AddIssueDTO testIssueDto = new AddIssueDTO("Test Issue", "",(long) 1, (long) 1, (long) 1, (long) 1, (long) (long) 1, (long) 1, null, null);
		return testIssueDto;
	}

	@Test
	public void connectionsTest() {
		assertNotNull(issueRepository);
		assertNotNull(issueDao);
	}

	@Test
	public void getAllIssuesTest() {
		assertNotNull(issueRepository.findAll());
		assertTrue(issueRepository.count() > 0);
	}
	
	@Test
	public void addIssue() throws Exception {
		AddIssueDTO testIssueDto = this.createTestIssue();
		long oldSize = issueRepository.count();
		issueService.addIssue(testIssueDto);
		long newSize = issueRepository.count();
		assertTrue(oldSize != newSize);
	}

	@Test
	public void changeTypeTest() throws IssueException, TypeException {
		Issue testIssue = issueRepository.findById(TEST_ISSUE_ID).get();
		long oldTypeId = testIssue.getType().getId();
		long nextDBIndex = ((++oldTypeId)%TYPES_COUNT)+1;
		issueService.changeIssueType(testIssue.getId(), new EditIssueDTO(nextDBIndex));
		long newTypeId = issueRepository.findById(TEST_ISSUE_ID).get().getType().getId();
		assertTrue(oldTypeId != newTypeId);
	}
	
	@Test
	public void changeStatusTest() throws IssueException, StatusException {
		Issue testIssue = issueRepository.findById(TEST_ISSUE_ID).get();
		long oldStatusId = testIssue.getStatus().getId();
		long nextDBIndex = ((++oldStatusId)%STATUSES_COUNT)+1;
		issueService.changeIssueStatus(testIssue.getId(), new EditIssueDTO(nextDBIndex));
		long newStatusId = issueRepository.findById(TEST_ISSUE_ID).get().getStatus().getId();
		assertTrue(oldStatusId != newStatusId);
	}
	
	@Test
	public void changePriorityTest() throws IssueException, PriorityException {
		Issue testIssue = issueRepository.findById(TEST_ISSUE_ID).get();
		long oldPriorityId = testIssue.getPriority().getId();
		long nextDBIndex = ((++oldPriorityId)%PRIORITIES_COUNT)+1;
		issueService.changeIssuePriority(testIssue.getId(), new EditIssueDTO(nextDBIndex));
		long newPriorityId = issueRepository.findById(TEST_ISSUE_ID).get().getPriority().getId();
		assertTrue(oldPriorityId != newPriorityId);
	}

	@Test
	public void deleteIssueTest() throws IssueException {
		long oldSize = issueRepository.count();
		issueDao.deleteIssueById(TEST_ISSUE_ID);
		long newSize = issueRepository.count();
		assertTrue(oldSize != newSize);
	}
}
