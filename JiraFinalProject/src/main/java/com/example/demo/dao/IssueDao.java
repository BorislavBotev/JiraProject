package com.example.demo.dao;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.demo.dto.DetailedIssueDTO;
import com.example.demo.dto.IssueOverviewDTO;
import com.example.demo.exceptions.IssueException;
import com.example.demo.repositories.IssueRepository;

@Component
public class IssueDao {
	public static final String IssueOverviewQuery = "select i.issue_id, i.issue_name, p.project_name, t.type_name, pr.priority_name\r\n" + 
			"from issues i join projects p on(i.project_id = p.project_id )\r\n" + 
			"join `types` t on (i.type_id = t.type_id)\r\n" + 
			"join priorities pr on (i.priority_id = pr.priority_id)";
	public static final String AssignedIssuesOverview = IssueOverviewQuery + " where i.asignee_user_id = ";
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private IssueRepository issueRepository;
	
//	public List<IssueOverviewDTO> getAllIssuesOverview() {
//		List<IssueOverviewDTO> issues = jdbcTemplate.query(IssueOverviewQuery, issueOverviewRowMapper());
//		return issues!=null ? issues : new LinkedList<IssueOverviewDTO>();
//	}
	
	public List<IssueOverviewDTO> getAllIssuesOverview() {
		List<IssueOverviewDTO> issues = issueRepository.findAll().stream()
							.map(issue -> new IssueOverviewDTO(issue.getId(), issue.getSummary(), issue.getProject().getName(), issue.getType().getName(), issue.getPriority().getName()))
							.collect(Collectors.toList());
		return issues!=null ? issues : new LinkedList<IssueOverviewDTO>();
	}
	
	
	public List<IssueOverviewDTO> getAssignedIssuesOverview(long userId) {
//		List<IssueOverviewDTO> issues = jdbcTemplate.query(AssignedIssuesOverview + userId, issueOverviewRowMapper());
		
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
		
		return issueRepository.findById(issueId).map(issue -> new DetailedIssueDTO(issue.getId(), issue.getSummary(), issue.getProject().getName(), issue.getSprint().getName(), issue.getDescription(), 
				issue.getType().getName(), issue.getPriority().getName(), issue.getStatus().getName(), 
				issue.getCreateDate(), issue.getLastUpdateDate(), issue.getReporterUser().getUsername(), issue.getAsigneeUser().getUsername())).get();
	}
	
//	private RowMapper<IssueOverviewDTO> issueOverviewRowMapper() {
//		return (rs, r) -> new IssueOverviewDTO(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
//	}
}
