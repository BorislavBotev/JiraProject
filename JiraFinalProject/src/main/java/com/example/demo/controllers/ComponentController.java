package com.example.demo.controllers;

import java.io.IOException;

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
import com.example.demo.dao.ComponentDAO;
import com.example.demo.dto.ComponentInfoDTO;
import com.example.demo.dto.CreateComponentDTO;
import com.example.demo.exceptions.InvalidComponentException;
import com.example.demo.model.Component;
import com.example.demo.repositories.ProjectRepository;
@RestController
public class ComponentController {
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private UserCheck usercheck;
	@Autowired
	private ComponentDAO componentDao;
	@PostMapping("projects/{id}/createComponent")
	public void createNewComponent(@PathVariable Long id,@RequestBody CreateComponentDTO component,HttpServletRequest request, HttpServletResponse response) {
		if(!usercheck.loggedAndAdmin(request, response)) {
			return;
		}
		if(projectRepository.getOne(id)==null) {
			System.out.println("invalid project id");
			response.setStatus(400);
			return;
		}
		try {
			componentDao.createComponent(component,id);
		} catch (InvalidComponentException e) {
			response.setStatus(400);
			System.out.println(e.getMessage());
		}
	}
	@DeleteMapping("project/{projectId}/component/delete/{componentId}")
	public void deleteComponent(@PathVariable Long projectId,@PathVariable Long componentId,
			HttpServletRequest request,HttpServletResponse response) {
		if(!usercheck.loggedAndAdmin(request, response)) {
			return;
		}
		if(projectRepository.getOne(projectId)==null) {
			System.out.println("invalid project id");
			response.setStatus(400);
			return;
		}
		try {
			componentDao.deleteComponentById(componentId);
		} catch (InvalidComponentException e) {
			try {
				response.sendError(400, e.getMessage());
			} catch (IOException e1) {
				response.setStatus(400);
				e1.printStackTrace();
			}
		}
	}
	@GetMapping("project/{projectId}/component/{componentId}")
	public ComponentInfoDTO showComponent(@PathVariable Long projectId,@PathVariable Long componentId,
			HttpServletRequest request,HttpServletResponse response) {
		if(!usercheck.isLoggedIn(request, response)) {
			return null;
		}
		if(projectRepository.getOne(projectId)==null) {
			System.out.println("invalid project id");
			response.setStatus(400);
			return null;
		}
		try {
			return componentDao.showInfoById(componentId);
		} catch (InvalidComponentException e) {
			try {
				response.sendError(400, e.getMessage());
			} catch (IOException e1) {
				response.setStatus(400);
				e1.printStackTrace();
			}
		}
		return null;
	}
}
