package com.example.demo.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AddWorklogDTO;
import com.example.demo.dto.WorklogDTO;
import com.example.demo.dto.WorklogIssueStatisticsDTO;
import com.example.demo.dto.WorklogUserStatisticsDTO;
import com.example.demo.exceptions.IssueException;
import com.example.demo.exceptions.ProjectException;
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
	Comparator<WorklogIssueStatisticsDTO> issueHourComparator = (i1,i2) -> i2.getTotalHoursSpent() - i1.getTotalHoursSpent();
	Comparator<WorklogUserStatisticsDTO> userHourComparator = (u1,u2) -> u2.getTotalHoursSpent() - u1.getTotalHoursSpent();
	
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
	
	
	public Set<WorklogIssueStatisticsDTO> getWorklogIssueStatistics() {
		Map<Issue,Integer> issueTotalSpentHours = new HashMap<Issue, Integer>();
		
		issueRepository.findAll().forEach(issue -> {
			if(worklogByIssueIsPresent(issue)) {
				Integer hours = worklogRepository.findAllByIssueId(issue.getId()).stream()
						.map(worklog -> worklog.getHoursSpent())
						.reduce((sum, value) -> sum+value)
						.get();
				issueTotalSpentHours.put(issue, hours);
			}
		});
		
		Set<WorklogIssueStatisticsDTO> issueStatistics = new TreeSet<WorklogIssueStatisticsDTO>(issueHourComparator);
		issueTotalSpentHours.forEach((issue, hours) ->  issueStatistics.add(new WorklogIssueStatisticsDTO(issue.getId(), issue.getSummary(), hours)));
		return issueStatistics;
	}
	
	
	public Set<WorklogUserStatisticsDTO> getWorklogUserStatistics() {
		Map<User,Integer> userTotalSpentHours = new HashMap<User, Integer>();
		
		userRepository.findAll().forEach(user -> {
			if(worklogByUserIsPresent(user)) {
				Integer hours = worklogRepository.findAllByUserId(user.getId()).stream()
						.map(worklog -> worklog.getHoursSpent())
						.reduce((sum, value) -> sum+value)
						.get();
				userTotalSpentHours.put(user, hours);
			}
		});
		
		Set<WorklogUserStatisticsDTO> userStatistics = new TreeSet<WorklogUserStatisticsDTO>(userHourComparator);
		userTotalSpentHours.forEach((user, hours) ->  userStatistics.add(new WorklogUserStatisticsDTO(user.getId(), user.getUsername(), hours)));
		return userStatistics;
	}
	
	
	public void deleteWorklog(long worklogId) throws WorklogException {
		if(!worklogRepository.findById(worklogId).isPresent()) {
			throw new WorklogException("Worklog Not Found!");
		}
		worklogRepository.deleteById(worklogId);
	}
	
	
	private boolean worklogByIssueIsPresent(Issue issue) {
		if(issue == null) {
			return false;
		}
		return worklogRepository.findAll().stream()
				.map(wl -> wl.getIssue().getId())
				.collect(Collectors.toSet())
				.contains(issue.getId());
	}
	
	private boolean worklogByUserIsPresent(User user) {
		if(user == null) {
			return false;
		}
		return worklogRepository.findAll().stream()
				.map(wl -> wl.getUser().getId())
				.collect(Collectors.toSet())
				.contains(user.getId());
	}
}
