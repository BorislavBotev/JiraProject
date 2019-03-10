package com.example.demo.test;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import java.time.LocalDate;
import java.util.stream.Collectors;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.JiraFinalProjectApplicationTests;
import com.example.demo.dto.CreateSprintDTO;
import com.example.demo.exceptions.InvalidDateException;
import com.example.demo.exceptions.InvalidNameException;
import com.example.demo.exceptions.InvalidStatusexception;
import com.example.demo.repositories.ProjectRepository;
import com.example.demo.repositories.SprintRepository;
import com.example.demo.service.SprintService;

public class SprintTest extends JiraFinalProjectApplicationTests{
	@Autowired
	private SprintRepository sprintRepository;
	@Autowired
	private SprintService sprintService;
	@Autowired
	private ProjectRepository projectRepository;
//	@Test
//	public void createSprint() throws InvalidNameException, InvalidDateException, InvalidStatusexception {
//		if(projectRepository.count()!=0) {
//			Long projectId=projectRepository.findAll().get(0).getId();
//			int sprintCountBefore=sprintRepository.findAll().stream().
//					filter(s->s.getProject().getId().equals(projectId)).collect(Collectors.toList()).size();
//			sprintService.createSprint(new CreateSprintDTO("Test",LocalDate.now(),LocalDate.now(),(long) 1), projectId);
//			int sprintCountAfter=sprintRepository.findAll().stream().
//					filter(s->s.getProject().getId().equals(projectId)).collect(Collectors.toList()).size();
//			assertNotSame(sprintCountBefore,sprintCountAfter);	
//			sprintRepository.deleteById(sprintRepository.findAll().stream().
//					filter(s->s.getName().equals("Test")).findFirst().get().getId());
//			assertSame(sprintCountBefore, sprintRepository.findAll().stream().
//					filter(s->s.getProject().getId().equals(projectId)).collect(Collectors.toList()).size());
//		}
//	}
}
