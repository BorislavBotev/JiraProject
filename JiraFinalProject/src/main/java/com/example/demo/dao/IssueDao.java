package com.example.demo.dao;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dto.DetailedIssueDTO;
import com.example.demo.dto.IssueOverviewDTO;
import com.example.demo.exceptions.IssueException;
import com.example.demo.exceptions.UserException;
import com.example.demo.repositories.IssueRepository;
import com.example.demo.repositories.UserRepository;

@Component
public class IssueDao {

	@Autowired
	private IssueRepository issueRepository;
	@Autowired
	private UserRepository userRepository;

	
	/**
	 * Get general review of all issues
	 * @return - list with general review of all issues
	 */
	public List<IssueOverviewDTO> getAllIssuesOverview() {
		List<IssueOverviewDTO> issues = issueRepository.findAll().stream()
							.map(issue -> new IssueOverviewDTO(issue.getId(), issue.getSummary(), issue.getProject().getName(), issue.getType().getName(), issue.getPriority().getName()))
							.collect(Collectors.toList());
		return issues!=null ? issues : new LinkedList<IssueOverviewDTO>();
	}
	
	
	/**
	 * Get general review of assigned issues to user 
	 * @param userId - ID of user in database
	 * @return - list with general review of all assigned issues to some user
	 * @throws UserException - when user is not present in database
	 */
	public List<IssueOverviewDTO> getAssignedIssuesOverview(long userId) throws UserException {
		if(!userRepository.findById(userId).isPresent()) {
			throw new UserException("User Not Found!");
		}
		List<IssueOverviewDTO> issues = issueRepository.findAll().stream()
								.filter(issue -> issue.getAsigneeUser().getId() == userId)
								.map(issue ->new IssueOverviewDTO(issue.getId(), issue.getSummary(), issue.getProject().getName(), issue.getType().getName(), issue.getPriority().getName()))
								.collect(Collectors.toList());
		return issues!=null ? issues : new LinkedList<IssueOverviewDTO>();
	}

	
	/**
	 * Get detailed view of issue by ID
	 * @param issueId - ID of issue object in database
	 * @return - DTO which contains detailed information about issue
	 * @throws IssueException - when issue is not present in database
	 */
	public DetailedIssueDTO getIssueDetails(long issueId) throws IssueException {
		if(!issueRepository.findById(issueId).isPresent()) {
			throw new IssueException("Issue Not Found!");
		}
		
		return issueRepository.findById(issueId).map(issue -> new DetailedIssueDTO(issue.getId(), issue.getSummary(), issue.getProject().getName(), "", issue.getDescription(), 
				issue.getType().getName(), issue.getPriority().getName(), issue.getStatus().getName(), 
				issue.getCreateDate(), issue.getLastUpdateDate(), issue.getReporterUser().getUsername(), issue.getAsigneeUser().getUsername())).get();
	}
}
