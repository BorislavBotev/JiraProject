package com.example.demo.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.check.UserCheck;
import com.example.demo.dao.ProjectDAO;
import com.example.demo.dto.CreateProjectDTO;
import com.example.demo.exceptions.ProjectException;

@RestController
public class ProjectController {
	@Autowired
	private ProjectDAO projectDao;
	@Autowired
	private UserCheck uc;
	@PostMapping("/createProject")
	public void createNewProject(@RequestBody CreateProjectDTO project,HttpServletRequest request,HttpServletResponse response) {
		if(!uc.loggedAndAdmin(request, response)) {
			return;
		}
		try {
			projectDao.createProject(project);
		} catch (ProjectException e) {
			System.out.println(e.getMessage());
			response.setStatus(400);
		}
	}
	
}
