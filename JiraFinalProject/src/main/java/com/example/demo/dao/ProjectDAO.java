package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dto.CreateProjectDTO;
import com.example.demo.dto.ViewComponentDto;
import com.example.demo.exceptions.ProjectException;
import com.example.demo.model.Project;
import com.example.demo.model.User;
import com.example.demo.repositories.ComponentRepository;
import com.example.demo.repositories.IssueRepository;
import com.example.demo.repositories.ProjectRepository;
import com.example.demo.repositories.SprintRepository;
import com.example.demo.repositories.UserRepository;


@Component
public class ProjectDAO {
	@Autowired
	private UserDAO userDao;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private ComponentRepository componentRepository;
	
	/**
	 * Creates teacher by a given createProjectDTO and saving it in the Database
	 * @param project - CreateProjectDTO 
	 * @param request -HttpServletRequest
	 * @return void
	 * @throws ProjectException
	 */
	public void createProject(CreateProjectDTO project,HttpServletRequest request) throws ProjectException {
		boolean nameAlreadyexists=projectRepository.findAll().stream().filter(u->u.getName().equals(project.getName()))
				.findAny().isPresent();
		if(nameAlreadyexists) {
			throw new ProjectException("Project with this id already exists");
		}
		Project p=new Project(null,project.getName());
		projectRepository.save(p);
		
	}
	/**
	 * Get all components from a project
	 * @param id - Id of the project the components are in
	 * @return List of ViewComponentDto objects
	 */
	public List<ViewComponentDto> listAllComponentsFromproject(Long id){
		return componentRepository.findAll().stream()
		.filter(c->c.getProject().getId().equals(id)).map(ViewComponentDto::new).collect(Collectors.toList());
		
	}
	
}
