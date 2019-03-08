package com.example.demo.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.check.UserCheck;
import com.example.demo.dao.IssueDao;
import com.example.demo.dto.AddCommentDTO;
import com.example.demo.dto.AddIssueDTO;
import com.example.demo.dto.DetailedIssueDTO;
import com.example.demo.dto.EditIssueDTO;
import com.example.demo.dto.IssueOverviewDTO;
import com.example.demo.exceptions.IssueException;
import com.example.demo.exceptions.PriorityException;
import com.example.demo.exceptions.StatusException;
import com.example.demo.exceptions.TypeException;
import com.example.demo.service.CommentService;
import com.example.demo.service.IssueService;

@RestController
public class IssueController {
	
	@Autowired
	private IssueDao issueDao;
	@Autowired
	private IssueService issueService;
	@Autowired
	private UserCheck usercheck;
	
	
	@GetMapping("/issues")
	public List<IssueOverviewDTO> listAllIssuesOverview(HttpServletRequest request, HttpServletResponse response) {
		if(!usercheck.isLoggedIn(request, response)) {
			return null;
		}
		return issueDao.getAllIssuesOverview();
	}
	
	@GetMapping("/issues/myIssues")
	public List<IssueOverviewDTO> listAssignedIssuesOverview(HttpServletRequest request, HttpServletResponse response) {
		if(!usercheck.isLoggedIn(request, response)) {
			return null;
		}
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("userId");
		return issueDao.getAssignedIssuesOverview(userId);
	}
	
	@GetMapping("/issues/{id}")
	public DetailedIssueDTO getIssueDetails(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) {
		if(!usercheck.isLoggedIn(request, response)) {
			return null;
		}
		try {
			return issueDao.getIssueDetails(id);
		} catch (IssueException e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
	}
	
	@PostMapping("/issues/addIssue")
	public void addIssue(@RequestBody AddIssueDTO newIssue, HttpServletRequest request, HttpServletResponse response) {
		if(!usercheck.isLoggedIn(request, response)) {
			return;
		}
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
	
	@PutMapping("issues/{issueId}/changeStatus")
	public void changeIssueStatus(@PathVariable long issueId,@RequestBody EditIssueDTO newStatus, HttpServletRequest request, HttpServletResponse response) {
		if(!usercheck.isLoggedIn(request, response)) {
			return;
		}
		try {
			issueService.changeIssueStatus(issueId, newStatus);
		} catch (IssueException | StatusException e) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@PutMapping("issues/{issueId}/changeType")
	public void changeIssueType(@PathVariable long issueId, @RequestBody EditIssueDTO newType, HttpServletRequest request, HttpServletResponse response) {
		if(!usercheck.isLoggedIn(request, response)) {
			return;
		}
		try {
			issueService.changeIssueType(issueId, newType);
		} catch (IssueException | TypeException e) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@PutMapping("issues/{issueId}/changePriority")
	public void changeIssuePriority(@PathVariable long issueId, @RequestBody EditIssueDTO newPriority, HttpServletRequest request, HttpServletResponse response) {
		if(!usercheck.isLoggedIn(request, response)) {
			return;
		}
		try {
			issueService.changeIssuePriority(issueId, newPriority);
		} catch (IssueException | PriorityException e) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	@DeleteMapping("issues/delete/{issueId}")
	public void deleteIssue(@PathVariable long issueId, HttpServletResponse response, HttpServletRequest request) {
		if(!usercheck.isLoggedIn(request, response)) {
			return;
		}
		try {
			issueDao.deleteIssueById(issueId);
		} catch (IssueException e) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
