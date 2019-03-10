package com.example.demo.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.JiraFinalProjectApplicationTests;
import com.example.demo.dao.ComponentDAO;
import com.example.demo.dto.CreateComponentDTO;
import com.example.demo.exceptions.InvalidComponentException;
import com.example.demo.model.Component;
import com.example.demo.repositories.ComponentRepository;
import com.example.demo.repositories.ProjectRepository;

public class ComponentTest extends JiraFinalProjectApplicationTests{
	@Autowired
	private ComponentRepository componentRepository;
	@Autowired
	private ProjectRepository projectReposioty;
	@Autowired
	private ComponentDAO componentDao;
	
	@Test
	public void createComponent() throws InvalidComponentException {
		CreateComponentDTO dto=new CreateComponentDTO("Test"+new Random().nextInt(10000),"Random");
		long countBefore=componentRepository.count();
		if(projectReposioty.count()!=0) {
			componentDao.createComponent(dto, projectReposioty.findAll().get(0).getId());
			long countAfter=componentRepository.findAll().size();
			assertNotSame(countBefore, countAfter);
			//componentRepository.delete(component);
		}
	}
	
}
