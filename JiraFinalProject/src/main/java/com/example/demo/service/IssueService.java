package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AddIssueDTO;
import com.example.demo.exceptions.IssueException;
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
	
	
	public void addIssue(AddIssueDTO newIssue) throws Exception {
		Issue issue = new Issue();
		issue.setCreateDate(LocalDateTime.now());
		issue.setLastUpdateDate(LocalDateTime.now());
		
		try {
			//NotNull Fields
			issue.setSummary(newIssue.getIssueName());
			issue.setType(typeRepository.findById(newIssue.getTypeId()).get());
			issue.setStatus(statusRepository.findById(newIssue.getStatusId()).get());
			issue.setPriority(priorityRepository.findById(newIssue.getPriorityId()).get());
			issue.setReporterUser(userRepository.findById(newIssue.getReporterUserId()).get());
			issue.setProject(projectRepository.findById(newIssue.getProjectId()).get());
		} catch (IssueException | NoSuchElementException e) {
			throw new Exception(e.getMessage(), e);
		}
		
		//Optional Fields
		if(isValidAsigneeUser(newIssue)) {
			issue.setAsigneeUser(userRepository.findById(newIssue.getAsigneeUserId()).get());
		}
		if(isValidComponent(newIssue)) {
			issue.setComponent(componentRepository.findById(newIssue.getComponentId()).get());
		}
		if(isValidSprint(newIssue)) {
			issue.setSprint(sprintRepository.findById(newIssue.getSprintId()).get());
		}
		if(isValidDescription(newIssue)) {
			issue.setDescription(newIssue.getDescription());
		}
		
		issueRepository.save(issue);
	}


	
	
	private boolean isValidDescription(AddIssueDTO newIssue) {
		return newIssue.getDescription() != null && newIssue.getDescription().trim().length() > 0;
	}

	private boolean isValidSprint(AddIssueDTO newIssue) {
		return newIssue.getSprintId() != null && sprintRepository.findById(newIssue.getSprintId()).isPresent();
	}

	private boolean isValidComponent(AddIssueDTO newIssue) {
		return newIssue.getComponentId() != null && componentRepository.findById(newIssue.getComponentId()).isPresent();
	}

	private boolean isValidAsigneeUser(AddIssueDTO newIssue) {
		return newIssue.getAsigneeUserId() != null && userRepository.findById(newIssue.getAsigneeUserId()).isPresent();
	}
}
