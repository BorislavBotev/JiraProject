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
	private ProjectRepository projectRepository;
	@Autowired
	private UserDAO userDao;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private IssueRepository issueRepository;
	@Autowired
	private SprintRepository sprintRepository;
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
		User user=userDao.getCurrentUser(request);
		user.getProjects().add(p);
		
	}
	public List<ViewComponentDto> listAllComponentsFromproject(Long id){
		return componentRepository.findAll().stream()
		.filter(c->c.getProject().getId().equals(id)).map(ViewComponentDto::new).collect(Collectors.toList());
		
	}
	@Transactional
	public void deleteProjectById(Long id) {
		issueRepository.findAll().stream().
		filter(i->i.getProject().getId().equals(id)).
		forEach(i->issueRepository.deleteById(i.getId()));
		
		componentRepository.findAll().stream().
		filter(c->c.getProject().getId().equals(id)).
		forEach(c->componentRepository.deleteById(c.getId()));
		
		sprintRepository.findAll().stream().
		filter(s->s.getProject().getId().equals(id)).
		forEach(s->sprintRepository.deleteById(s.getId()));
		
		projectRepository.deleteById(id);	
		
	}
	
}
