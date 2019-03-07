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
import com.example.demo.dto.CreateComponentDTO;
import com.example.demo.exceptions.InvalidComponentException;
import com.example.demo.repositories.ProjectRepository;
@RestController
public class ComponentController {
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private UserCheck uc;
	@Autowired
	private ComponentDAO componentDao;
	@PostMapping("projects/{id}/createComponent")
	public void createNewComponent(@PathVariable Long id,@RequestBody CreateComponentDTO component,HttpServletRequest request, HttpServletResponse response) {
		if(!uc.loggedAndAdmin(request, response)) {
			return;
		}
		if(projectRepository.getOne(id)==null) {
			System.out.println("invalid project id");
			response.setStatus(400);
		}
		try {
			componentDao.createComponent(component,id);
		} catch (InvalidComponentException e) {
			response.setStatus(400);
			System.out.println(e.getMessage());
		}
	}
}
