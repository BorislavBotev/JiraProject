package com.example.demo.dao;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dto.DetailedIssueDTO;
import com.example.demo.dto.IssueOverviewDTO;
import com.example.demo.exceptions.IssueException;
import com.example.demo.repositories.IssueRepository;

@Component
public class IssueDao {

	@Autowired
	private IssueRepository issueRepository;

	
	public List<IssueOverviewDTO> getAllIssuesOverview() {
		List<IssueOverviewDTO> issues = issueRepository.findAll().stream()
							.map(issue -> new IssueOverviewDTO(issue.getId(), issue.getSummary(), issue.getProject().getName(), issue.getType().getName(), issue.getPriority().getName()))
							.collect(Collectors.toList());
		return issues!=null ? issues : new LinkedList<IssueOverviewDTO>();
	}
	
	

	public List<IssueOverviewDTO> getAssignedIssuesOverview(long userId) {
		List<IssueOverviewDTO> issues = issueRepository.findAll().stream()
								.filter(issue -> issue.getAsigneeUser().getId() == userId)
								.map(issue ->new IssueOverviewDTO(issue.getId(), issue.getSummary(), issue.getProject().getName(), issue.getType().getName(), issue.getPriority().getName()))
								.collect(Collectors.toList());
		return issues!=null ? issues : new LinkedList<IssueOverviewDTO>();
	}

	
	public DetailedIssueDTO getIssueDetails(long issueId) throws IssueException {
		if(!issueRepository.findById(issueId).isPresent()) {
			throw new IssueException("Value Not Found!");
		}
		
		return issueRepository.findById(issueId).map(issue -> new DetailedIssueDTO(issue.getId(), issue.getSummary(), issue.getProject().getName(), "", issue.getDescription(), 
				issue.getType().getName(), issue.getPriority().getName(), issue.getStatus().getName(), 
				issue.getCreateDate(), issue.getLastUpdateDate(), issue.getReporterUser().getUsername(), issue.getAsigneeUser().getUsername())).get();
	}
	
}
