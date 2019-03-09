package com.example.demo.service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AddWorklogDTO;
import com.example.demo.exceptions.IssueException;
import com.example.demo.exceptions.ProjectException;
import com.example.demo.exceptions.UserException;
import com.example.demo.exceptions.WorklogException;
import com.example.demo.model.Issue;
import com.example.demo.model.User;
import com.example.demo.model.Worklog;
import com.example.demo.repositories.IssueRepository;
import com.example.demo.repositories.ProjectRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.repositories.WorklogRepository;

@Service
public class WorklogService {
	
	@Autowired
	private WorklogRepository worklogRepository;
	@Autowired
	private IssueRepository issueRepository;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired 
	private UserRepository userRepository;
	
	
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
	
	
	public List<WorklogDTO> getWorklogOfProject(long projectId) throws ProjectException {
		if(!projectRepository.findById(projectId).isPresent()) {
			throw new ProjectException("Project Not Found!");
		}
		return worklogRepository.findAll().stream()
				.filter(wl -> wl.getIssue().getProject().getId() == projectId)
				.map(wl -> new WorklogDTO(wl.getId(), wl.getUser().getUsername(), wl.getIssue().getSummary(),
							wl.getDescription(), wl.getHoursSpent(), wl.getHoursRemaining(), wl.getLogDateTime()))
				.collect(Collectors.toList());
	}
	
	
	public List<WorklogDTO> getWorklogOfIssue(long projectId, long issueId) throws ProjectException, IssueException {
		if(!projectRepository.findById(projectId).isPresent()) {
			throw new ProjectException("Project Not Found!");
		}
		if(!issueRepository.findById(issueId).isPresent()) {
			throw new IssueException("Issue Not Found!");
		}
		
		return worklogRepository.findAll().stream()
				.filter(wl -> wl.getIssue().getId() == issueId)
				.map(wl -> new WorklogDTO(wl.getId(), wl.getUser().getUsername(), wl.getIssue().getSummary(),
							wl.getDescription(), wl.getHoursSpent(), wl.getHoursRemaining(), wl.getLogDateTime()))
				.collect(Collectors.toList());
	}
	
	
	public List<WorklogDTO> getWorklogOfCurrentUser(long userId) {
		return worklogRepository.findAll().stream()
				.filter(wl -> wl.getUser().getId() == userId)
				.map(wl -> new WorklogDTO(wl.getId(), wl.getUser().getUsername(), wl.getIssue().getSummary(),
							wl.getDescription(), wl.getHoursSpent(), wl.getHoursRemaining(), wl.getLogDateTime()))
				.collect(Collectors.toList());
	}
	
	
	
	
	
	public void deleteWorklog(long worklogId) throws WorklogException {
		if(!worklogRepository.findById(worklogId).isPresent()) {
			throw new WorklogException("Worklog Not Found!");
		}
		worklogRepository.deleteById(worklogId);
	}
}
