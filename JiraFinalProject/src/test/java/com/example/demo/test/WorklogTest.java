package com.example.demo.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.JiraFinalProjectApplicationTests;
import com.example.demo.exceptions.WorklogException;
import com.example.demo.repositories.WorklogRepository;
import com.example.demo.service.WorklogService;

public class WorklogTest extends JiraFinalProjectApplicationTests {

	@Autowired
	private WorklogService worklogService;
	@Autowired
	private WorklogRepository worklogRepository;
	
	
	@Test
	public void connectionsTest() {
		assertNotNull(worklogService);
		assertNotNull(worklogRepository);
	}
	
	
	@Test
	public void issueStatisticsTest() {
		if(worklogRepository.count() > 0) {
			assertNotNull(worklogService.getWorklogIssueStatistics());
		}
	}

	
	@Test
	public void userStatisticsTest() {
		if(worklogRepository.count() > 0) {
			assertNotNull(worklogService.getWorklogUserStatistics());
		}
	}
	
	
	@Test
	public void deleteWorklogTest() throws WorklogException {
		if(worklogRepository.count()>0) {
			long id;
			do {
				id = ((long) new Random().nextInt((int) worklogRepository.count())+1);				
			} while (!worklogRepository.findById(id).isPresent());
			System.out.println(id);
			long oldSize = worklogRepository.count();
			worklogService.deleteWorklog(id);
			long newSize = worklogRepository.count();
			assertTrue(oldSize == newSize+1);
		}
	}
}
