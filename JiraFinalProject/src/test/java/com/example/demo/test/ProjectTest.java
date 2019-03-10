package com.example.demo.test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.JiraFinalProjectApplicationTests;
import com.example.demo.dao.ProjectDAO;
import com.example.demo.dto.ViewComponentDto;
import com.example.demo.model.Project;
import com.example.demo.repositories.ComponentRepository;
import com.example.demo.repositories.ProjectRepository;

public class ProjectTest extends JiraFinalProjectApplicationTests{
	
	@Autowired
	private ProjectRepository projectReposioty;
	@Autowired
	private ProjectDAO projectDao;
	@Autowired
	private ComponentRepository componentRepository;
	@Test
	public void createProject() {
		int countProjects=projectReposioty.findAll().size();
		Project p=new Project(null,"Test project");
		projectReposioty.save(p);
		int countAfterCreating= projectReposioty.findAll().size();
		assertNotSame(countProjects, countAfterCreating);
		projectReposioty.delete(p);
	}
	
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
