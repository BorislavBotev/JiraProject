package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AddIssueDTO;
import com.example.demo.model.Issue;
import com.example.demo.repositories.ComponentRepository;
import com.example.demo.repositories.IssueRepository;
import com.example.demo.repositories.PriorityRepository;
import com.example.demo.repositories.ProjectRepository;
import com.example.demo.repositories.SprintRepository;
import com.example.demo.repositories.StatusRepository;
import com.example.demo.repositories.TypeRepository;
import com.example.demo.repositories.UserRepository;

@Service
public class IssueService {

	@Autowired
	private IssueRepository issueRepository;
	@Autowired
	private TypeRepository typeRepository;
	@Autowired
	private StatusRepository statusRepository;
	@Autowired
	private PriorityRepository priorityRepository;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private SprintRepository sprintRepository;
	@Autowired 
	private UserRepository userRepository;
	@Autowired
	private ComponentRepository componentRepository;
	
	
	public void addIssue(AddIssueDTO newIssue) {
//		Issue issue = new Issue(null, newIssue.getIssueName(), typeRepository.findById(newIssue.getTypeId()).get()
//				, newIssue.getPriorityId(), newIssue.getStatusId(), newIssue.getDescription(), LocalDateTime.now(), 
//				LocalDateTime.now(), newIssue.getProjectId(), newIssue.getSprintId(), 
//				newIssue.getReporterUserId(), newIssue.getAsigneeUserId(), newIssue.getComponentId());
		
		Issue issue = new Issue(null, newIssue.getIssueName(), typeRepository.findById(newIssue.getTypeId()).get(),
				statusRepository.findById(newIssue.getStatusId()).get(), 
				priorityRepository.findById(newIssue.getPriorityId()).get(),
				newIssue.getDescription(), LocalDateTime.now(), LocalDateTime.now(),
				projectRepository.findById(newIssue.getProjectId()).get(), 
				sprintRepository.findById(newIssue.getSprintId()).get(),
				userRepository.findById(newIssue.getReporterUserId()).get(),
				userRepository.findById(newIssue.getAsigneeUserId()).get(),
				componentRepository.findById(newIssue.getComponentId()).get());
		
		issueRepository.save(issue);
	}
}
