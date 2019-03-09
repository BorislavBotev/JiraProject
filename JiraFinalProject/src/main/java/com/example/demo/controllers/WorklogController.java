package com.example.demo.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.check.UserCheck;
import com.example.demo.dao.UserDAO;
import com.example.demo.dto.AddWorklogDTO;
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
}
