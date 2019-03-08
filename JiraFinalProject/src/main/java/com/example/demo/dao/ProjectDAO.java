package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dto.CreateProjectDTO;
import com.example.demo.dto.ViewComponentDto;
import com.example.demo.exceptions.ProjectException;
import com.example.demo.model.Project;
import com.example.demo.model.User;
import com.example.demo.repositories.ProjectRepository;


@Component
public class ProjectDAO {
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private UserDAO userDao;
	
	
	public void createProject(CreateProjectDTO project,HttpServletRequest request) throws ProjectException {
		boolean nameAlreadyexists=projectRepository.findAll().stream().filter(u->u.getName().equals(project.getName()))
				.findAny().isPresent();
		if(project.getName().trim().length()==0 || nameAlreadyexists) {
			throw new ProjectException("Invalid name");
		}
		Project p=new Project(null,project.getName());
		projectRepository.save(p);
		User user=userDao.getCurrentUser(request);
		//System.out.println(user);
		user.getProjects().add(p);
		p.getUsers().add(user);
		//System.out.println(user);

		
	}
	public List<ViewComponentDto> listAllComponentsFromproject(Long id){
		List<ViewComponentDto> dtos=new ArrayList<>();
		List<com.example.demo.model.Component>list=projectRepository.findById(id).get().getComponents();
		if(list!=null) {
			list.forEach(c->
				dtos.add(new ViewComponentDto(c.getId(),c.getName(),c.getDescription(),c.getIssues()))
			);
			return dtos;
		}
		System.out.println("nqma componenti");
		return null;
	}
}
