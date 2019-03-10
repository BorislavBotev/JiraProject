package com.example.demo.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.check.UserCheck;
import com.example.demo.dao.UserDAO;
import com.example.demo.dto.AddWorklogDTO;
import com.example.demo.dto.WorklogDTO;
import com.example.demo.dto.WorklogIssueStatisticsDTO;
import com.example.demo.dto.WorklogUserStatisticsDTO;
import com.example.demo.exceptions.IssueException;
import com.example.demo.exceptions.ProjectException;
import com.example.demo.exceptions.WorklogException;
import com.example.demo.model.User;
import com.example.demo.service.WorklogService;

@RestController
public class WorklogController {

	
	@Autowired
	private WorklogService worklogService;
	@Autowired
	private UserCheck usercheck;
	@Autowired
	private UserDAO userDao;
	
	
	@PostMapping("/{projectId}/issues/{issueId}/addWorklog")
	public void addWorklog(@PathVariable long projectId, @PathVariable long issueId, @RequestBody AddWorklogDTO worklog, HttpServletRequest request, HttpServletResponse response) {
		if(!usercheck.isLoggedIn(request, response)) {
			return;
		}
		User currentUser = userDao.getCurrentUser(request);
		try {
			worklogService.addWorklog(currentUser, worklog, projectId, issueId);
		} catch (ProjectException | IssueException | WorklogException e) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	@GetMapping("/{projectId}/worklog")
	public List<WorklogDTO> getWorklogOfProject(@PathVariable long projectId, HttpServletRequest request, HttpServletResponse response){
		if(!usercheck.isLoggedIn(request, response)) {
			return null;
		}
		try {
			return worklogService.getWorklogOfProject(projectId);
		} catch (ProjectException e) {
			try {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return null;
		}
	}
	
	
	@GetMapping("/{projectId}/issues/{issueId}/worklog")
	public List<WorklogDTO> getWorklogOfIssue(@PathVariable long projectId, @PathVariable long issueId, HttpServletRequest request, HttpServletResponse response) {
		if(!usercheck.isLoggedIn(request, response)) {
			return null;
		}
		try {
			return worklogService.getWorklogOfIssue(projectId, issueId);
		} catch (ProjectException | IssueException e) {
			try {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return null;
		}
	}
	
	
	@GetMapping("/myWorklog")
	public List<WorklogDTO> getWorkLogOfCurrentUser(HttpServletRequest request, HttpServletResponse response) {
		if(!usercheck.isLoggedIn(request, response)) {
			return null;
		}
		long userId = userDao.getCurrentUser(request).getId();
		return worklogService.getWorklogOfCurrentUser(userId);
	}
	
	
	@GetMapping("/worklog/issueStatistics")
	public Set<WorklogIssueStatisticsDTO> getWorklogIssueStatistics(HttpServletRequest request, HttpServletResponse response) {
		if(!usercheck.isLoggedIn(request, response)) {
			return null;
		}
		return worklogService.getWorklogIssueStatistics();
	}
	
	
	@GetMapping("/worklog/userStatistics")
	public Set<WorklogUserStatisticsDTO> getWorklogUserStatistics(HttpServletRequest request, HttpServletResponse response) {
		if(!usercheck.isLoggedIn(request, response)) {
			return null;
		}
		return worklogService.getWorklogUserStatistics();
	}
	
	
	@DeleteMapping("/worklog/delete/{worklogId}")
	public void deleteWorklogByID(@PathVariable long worklogId, HttpServletRequest request, HttpServletResponse response) {
		if(!usercheck.isLoggedIn(request, response)) {
			return;
		}
		if(!usercheck.loggedAndAdmin(request, response)){
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		try {
			worklogService.deleteWorklog(worklogId);
		} catch (WorklogException e) {
			try {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
