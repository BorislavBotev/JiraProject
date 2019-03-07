package com.example.demo.dao;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dto.CreateProjectDTO;
import com.example.demo.exceptions.ProjectException;
import com.example.demo.model.Project;
import com.example.demo.repositories.ProjectRepository;


@Component
public class ProjectDAO {
	@Autowired
	private ProjectRepository repository;
	public void createProject(CreateProjectDTO project) throws ProjectException {
		boolean nameAlreadyexists=repository.findAll().stream().filter(u->u.getName().equals(project.getName()))
				.findAny().isPresent();
		if(project.getName().trim().length()==0 || nameAlreadyexists) {
			throw new ProjectException("Invalid name");
		}
		Project p=new Project(null,project.getName());
		repository.save(p);
	}
}
