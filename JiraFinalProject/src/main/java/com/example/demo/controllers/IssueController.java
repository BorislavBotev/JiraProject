package com.example.demo.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.IssueDao;
import com.example.demo.dto.AddIssueDTO;
import com.example.demo.dto.DetailedIssueDTO;
import com.example.demo.dto.IssueOverviewDTO;
import com.example.demo.service.IssueService;

@RestController
public class IssueController {
	
	@Autowired
	private IssueDao issueDao;
	@Autowired
	private IssueService issueService;
	
	@GetMapping("/issues")
	public List<IssueOverviewDTO> listAllIssuesOverview() {
		return issueDao.getAllIssuesOverview();
	}
	
	@GetMapping("/issues/myIssues")
	public List<IssueOverviewDTO> listAssignedIssuesOverview(HttpServletRequest request) {
		HttpSession session = request.getSession();
//		Long userId = session.getAttribute("userId");
		return issueDao.getAssignedIssuesOverview(1);
	}
	
	@GetMapping("/issues/{id}")
	public DetailedIssueDTO getIssueDetails(@PathVariable long id) {
		return issueDao.getIssueDetails(id);
	}
	
	@PostMapping("/issues/addIssue")
	public void addIssue(@RequestBody AddIssueDTO newIssue) {
		issueService.addIssue(newIssue);
	}
}
