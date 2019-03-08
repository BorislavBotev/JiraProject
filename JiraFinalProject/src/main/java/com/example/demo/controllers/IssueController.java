package com.example.demo.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.IssueDao;
import com.example.demo.dao.UserDAO;
import com.example.demo.dto.AddIssueDTO;
import com.example.demo.dto.DetailedIssueDTO;
import com.example.demo.dto.IssueOverviewDTO;
import com.example.demo.exceptions.IssueException;
import com.example.demo.service.IssueService;

@RestController
public class IssueController {
	
	@Autowired
	private IssueDao issueDao;
	@Autowired
	private IssueService issueService;
	@Autowired
	private UserDAO userDao;
	
	@GetMapping("/issues")
	public List<IssueOverviewDTO> listAllIssuesOverview() {
		return issueDao.getAllIssuesOverview();
	}
	
	@GetMapping("/issues/myIssues")
	public List<IssueOverviewDTO> listAssignedIssuesOverview(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return issueDao.getAssignedIssuesOverview(userDao.getCurrentUser(request).getId());
	}
	
	@GetMapping("/issues/{id}")
	public DetailedIssueDTO getIssueDetails(@PathVariable long id, HttpServletResponse response) {
		try {
			return issueDao.getIssueDetails(id);
		} catch (IssueException e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
	}
	
	@PostMapping("/issues/addIssue")
	public void addIssue(@RequestBody AddIssueDTO newIssue, HttpServletResponse response) {
		try {
			issueService.addIssue(newIssue);
		} catch (Exception e) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
