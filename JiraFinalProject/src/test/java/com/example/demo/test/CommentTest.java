package com.example.demo.test;

import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.JiraFinalProjectApplicationTests;
import com.example.demo.dto.CommentDTO;
import com.example.demo.exceptions.IssueException;
import com.example.demo.repositories.CommentRepository;
import com.example.demo.repositories.IssueRepository;
import com.example.demo.service.CommentService;

public class CommentTest extends JiraFinalProjectApplicationTests{
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private CommentService commentService;
	@Autowired
	private IssueRepository issueRepository;
	
	private final Long issueId=issueRepository.findAll().stream().findFirst().get().getId();
	
	@Test
	public void getCommentsFromIssue() throws IssueException {
		List<CommentDTO> getIssueComments=commentService.getIssueComments(issueId);
		assertNotNull(getIssueComments);
	}
	
	@Test
	public void getUserComments() throws IssueException {
		List<CommentDTO> getUserComments=commentService.getIssueComments(issueId);
		assertNotNull(getUserComments);
	}
	
	@Test
	public void addComment() {
		
	}
}
