package com.example.demo.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.check.UserCheck;
import com.example.demo.dao.ComponentDAO;
import com.example.demo.dao.SprintDAO;
import com.example.demo.dto.CreateSprintDTO;
import com.example.demo.exceptions.InvalidDateException;
import com.example.demo.exceptions.InvalidNameException;
import com.example.demo.exceptions.InvalidStatusexception;
import com.example.demo.repositories.ProjectRepository;

@RestController
public class SprintController {
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private UserCheck uc;
	@Autowired
	private SprintDAO sprintDao;
	@PostMapping("projects/{id}/createSprint")
	public void createNewSprint(@PathVariable Long id,@RequestBody CreateSprintDTO sprint,
			HttpServletRequest request,HttpServletResponse response) {
		System.out.println("hiiiiiiiiiiiiii");
		if(!uc.loggedAndAdmin(request, response)) {
			return;
		}	
		if(projectRepository.getOne(id)==null) {
			System.out.println("invalid project id");
			response.setStatus(400);
			return;
		}
		try {
			sprintDao.createSprint(sprint,id);
		} catch (InvalidNameException | InvalidDateException | InvalidStatusexception e) {
			System.out.println(e.getMessage());
			response.setStatus(400);
		}
		
		
	}
}
