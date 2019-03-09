package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.check.UserCheck;
import com.example.demo.dao.ProjectDAO;
import com.example.demo.dto.CreateProjectDTO;
import com.example.demo.dto.ViewComponentDto;
import com.example.demo.exceptions.ProjectException;
import com.example.demo.repositories.ProjectRepository;

@RestController
public class ProjectController {
	@Autowired
	private ProjectDAO projectDao;
	@Autowired
	private UserCheck uc;
	@Autowired
	private ProjectRepository projectRepository;
	@PostMapping("/createProject")
	public void createNewProject(@RequestBody CreateProjectDTO project,HttpServletRequest request,HttpServletResponse response) {
		if(!uc.loggedAndAdmin(request, response)) {
			return;
		}
		try {
			projectDao.createProject(project, request);
		} catch (ProjectException e) {
			System.out.println(e.getMessage());
			response.setStatus(400);
		}
	}
	@GetMapping("project/{id}/components")
	public List<ViewComponentDto> listAllComponents(@PathVariable Long id,HttpServletRequest request,HttpServletResponse response){
		if(!uc.isLoggedIn(request, response)) {
			return null;
		}
		if(projectRepository.getOne(id)==null) {
			System.out.println("invalid project id");
			response.setStatus(400);
			return null;
		}
		return projectDao.listAllComponentsFromproject(id);
	}
}
