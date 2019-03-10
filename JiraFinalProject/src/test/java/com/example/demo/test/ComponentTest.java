package com.example.demo.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

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
		CreateComponentDTO dto=new CreateComponentDTO("Test"+new Random().nextInt(10000),"Random 90000");
		Long countBefore=componentRepository.count();
		if(countBefore!=0) {
			componentDao.createComponent(dto, projectReposioty.findAll().get(0).getId());
			long countAfter=componentRepository.count();
			assertNotSame(countBefore, countAfter);
		}
	}
	@Test
	public void deleteComponent() throws InvalidComponentException {
		long countBefore=componentRepository.count();
		if(countBefore!=0) {
			Long id=componentRepository.findAll().stream().
					filter(c->c.getDescription().equals("Random 90000")).findAny().get().getId();
			componentDao.deleteComponentById(id);
			long countAfter=componentRepository.count();
			assertNotSame(countBefore, countAfter);
		}
	}
	
}
