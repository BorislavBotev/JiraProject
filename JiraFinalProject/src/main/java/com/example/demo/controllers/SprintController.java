package com.example.demo.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.check.UserCheck;
import com.example.demo.dao.ComponentDAO;
import com.example.demo.dao.SprintDAO;
import com.example.demo.dao.UserDAO;
import com.example.demo.dto.CompleteSprintDTO;
import com.example.demo.dto.CreateSprintDTO;
import com.example.demo.exceptions.InvalidDateException;
import com.example.demo.exceptions.InvalidNameException;
import com.example.demo.exceptions.InvalidSprintException;
import com.example.demo.exceptions.InvalidStatusexception;
import com.example.demo.exceptions.IssueException;
import com.example.demo.model.Sprint;
import com.example.demo.model.User;
import com.example.demo.repositories.ProjectRepository;
import com.example.demo.service.SprintService;

@RestController
public class SprintController {
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private UserCheck usercheck;
	@Autowired
	private SprintService sprintService;
	@Autowired
	private SprintDAO sprintDao;
	@Autowired
	private UserDAO userDao;
	@PostMapping("projects/{id}/createSprint")
	public void createNewSprint(@PathVariable Long id,@RequestBody CreateSprintDTO sprint,
			HttpServletRequest request,HttpServletResponse response) {
		
		if(!usercheck.isLoggedIn(request, response)) {
			return;
		}	
		if(projectRepository.getOne(id)==null) {
			System.out.println("invalid project id");
			response.setStatus(404);
			return;
		}
		User user=userDao.getCurrentUser(request);
		try {
			sprintService.createSprint(sprint,id,user);
		} catch (InvalidNameException | InvalidDateException | InvalidStatusexception e) {
			System.out.println(e.getMessage());
			response.setStatus(400);
		}
	}
	
	
	@DeleteMapping("project/{projectId}/sprint/delete/{sprintId}")
	public void deleteSprint(@PathVariable Long projectId, @PathVariable Long sprintId,
			HttpServletResponse response, HttpServletRequest request) {
		if(!usercheck.loggedAndAdmin(request, response)) {
			return;
		}
		if(!projectRepository.findById(projectId).isPresent()) {
			try {
				response.sendError(404, "Project not found");
				return;
			} catch (IOException e) {
				//presponse.setStatus(404);
				e.printStackTrace();
			}
		}
		try {
			sprintDao.deleteSprintById(sprintId);
		} catch (InvalidSprintException e) {
			try {
				response.sendError(404, e.getMessage());
			} catch (IOException e1) {
				e.printStackTrace();
			}
		}
	}
	@PutMapping("project/{projectId}/sprint/complete/{sprintId}")
	public CompleteSprintDTO completeSprint(@PathVariable Long projectId, @PathVariable Long sprintId,
			HttpServletResponse response, HttpServletRequest request) {
		if(!usercheck.loggedAndAdmin(request, response)) {
			return null;
		}
		if(!projectRepository.findById(projectId).isPresent()) {
			try {
				response.sendError(404, "Invalid project id");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			return sprintDao.completeSprint(sprintId);
		} catch (InvalidSprintException e) {
			response.setStatus(404);			
			e.printStackTrace();
			return null;
		}
		
	}
}
