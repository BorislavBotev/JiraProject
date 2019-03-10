package com.example.demo.test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.JiraFinalProjectApplicationTests;
import com.example.demo.dao.ProjectDAO;
import com.example.demo.dto.CreateProjectDTO;
import com.example.demo.dto.ViewComponentDto;
import com.example.demo.exceptions.ProjectException;
import com.example.demo.model.Project;
import com.example.demo.repositories.ComponentRepository;
import com.example.demo.repositories.ProjectRepository;
import com.example.demo.service.ProjectService;

public class ProjectTest extends JiraFinalProjectApplicationTests{
	
	@Autowired
	private ProjectRepository projectReposioty;
	@Autowired
	private ProjectDAO projectDao;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private ComponentRepository componentRepository;
	private HttpServletRequest httpServletRequest;
	@Test
	public void createProject() throws ProjectException {
		Long countBefore=projectReposioty.count();
		Project p=new Project(null,"Random");
		projectReposioty.save(p);
		assertNotSame(countBefore, projectReposioty.count());
		projectReposioty.delete(p);
		assertSame(countBefore, projectReposioty.count());
	
	}
	
	@Test
	public void returnComponentsFromProject() {
		int size=projectReposioty.findAll().size();
		if(size!=0) {
			Project p=projectReposioty.findAll().get(new Random().nextInt(size));
			List<ViewComponentDto> dto=componentRepository.findAll().stream()
					.filter(c->c.getProject().getId().equals(p.getId())).map(ViewComponentDto::new).collect(Collectors.toList());
			assertNotNull(dto);
		}
	}

}
