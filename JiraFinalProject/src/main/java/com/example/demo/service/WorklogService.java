package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AddWorklogDTO;
import com.example.demo.exceptions.IssueException;
import com.example.demo.exceptions.ProjectException;
import com.example.demo.exceptions.WorklogException;
import com.example.demo.model.Issue;
import com.example.demo.model.User;
import com.example.demo.model.Worklog;
import com.example.demo.repositories.IssueRepository;
import com.example.demo.repositories.ProjectRepository;
import com.example.demo.repositories.WorklogRepository;

@Service
public class WorklogService {
	
	@Autowired
	private WorklogRepository worklogRepository;
	@Autowired
	private IssueRepository issueRepository;
	@Autowired
	private ProjectRepository projectRepository;
	
	public void addWorklog(User user, AddWorklogDTO worklogDTO, long projectId, long issueId) throws ProjectException, IssueException, WorklogException {
		if(!projectRepository.findById(projectId).isPresent()) {
			throw new ProjectException("Project Not Found!");
		}
		if(!issueRepository.findById(issueId).isPresent()) {
			throw new IssueException("Issue Not Found!");
		}
		if(worklogDTO.getHoursSpent() == null || worklogDTO.getHoursRemaining() == null) {
			throw new WorklogException("Invalid Worklog Data!");
		}
		Issue issue = issueRepository.findById(issueId).get();
		String description = worklogDTO.getDescription();
		int hoursSpent = worklogDTO.getHoursSpent();
		int hoursRemaining = worklogDTO.getHoursRemaining();
		Worklog worklog = new Worklog(user, issue, description, hoursSpent, hoursRemaining);
		worklogRepository.save(worklog);
	}

	}
