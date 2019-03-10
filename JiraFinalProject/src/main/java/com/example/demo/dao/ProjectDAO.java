package com.example.demo.dao;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dto.CreateProjectDTO;
import com.example.demo.dto.ViewComponentDto;
import com.example.demo.exceptions.ProjectException;
import com.example.demo.model.Project;
import com.example.demo.model.User;
import com.example.demo.repositories.ComponentRepository;
import com.example.demo.repositories.ProjectRepository;


@Component
public class ProjectDAO {
	
	@Autowired
	private UserDAO userDao;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private ComponentRepository componentRepository;
	
	
	public void createProject(CreateProjectDTO project,HttpServletRequest request) throws ProjectException {
		boolean nameAlreadyexists=projectRepository.findAll().stream().filter(u->u.getName().equals(project.getName()))
				.findAny().isPresent();
		if(project.getName().trim().length()==0 || nameAlreadyexists) {
			throw new ProjectException("Invalid name");
		}
		Project p=new Project(null,project.getName());
		projectRepository.save(p);
		User user = userDao.getCurrentUser(request);
	}
	
	
	public List<ViewComponentDto> listAllComponentsFromproject(Long id){
		return componentRepository.findAll().stream()
				.filter(c->c.getProject().getId().equals(id))
				.map(ViewComponentDto::new)
				.collect(Collectors.toList());
	}
}
