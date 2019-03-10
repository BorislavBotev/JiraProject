package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.dto.AddIssueDTO;
import com.example.demo.dto.ChangeUserDTO;
import com.example.demo.dto.DescriptionDTO;
import com.example.demo.dto.EditIssueDTO;
import com.example.demo.exceptions.IssueException;
import com.example.demo.exceptions.PriorityException;
import com.example.demo.exceptions.SprintException;
import com.example.demo.exceptions.StatusException;
import com.example.demo.exceptions.TypeException;
import com.example.demo.exceptions.UserException;
import com.example.demo.model.Issue;
import com.example.demo.model.PriorityModel;
import com.example.demo.model.StatusModel;
import com.example.demo.model.TypeModel;
import com.example.demo.model.User;
import com.example.demo.repositories.ComponentRepository;
import com.example.demo.repositories.IssueRepository;
import com.example.demo.repositories.PriorityRepository;
import com.example.demo.repositories.ProjectRepository;
import com.example.demo.repositories.SprintRepository;
import com.example.demo.repositories.StatusRepository;
import com.example.demo.repositories.TypeRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.repositories.WorklogRepository;

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
	@Autowired
	private WorklogRepository worklogRepository;
	
	
	public void addIssue(AddIssueDTO newIssue) throws Exception {
		Issue issue = new Issue();
		LocalDateTime createDate = LocalDateTime.now();
		issue.setCreateDate(createDate);
		issue.setLastUpdateDate(createDate);
		
		try {
			//NotNull Fields
			issue.setSummary(newIssue.getIssueName());
			issue.setType(typeRepository.findById(newIssue.getTypeId()).get());
			issue.setStatus(statusRepository.findById(newIssue.getStatusId()).get());
			issue.setPriority(priorityRepository.findById(newIssue.getPriorityId()).get());
			issue.setReporterUser(userRepository.findById(newIssue.getReporterUserId()).get());
			issue.setAsigneeUser(userRepository.findById(newIssue.getAsigneeUserId()).get());
			issue.setProject(projectRepository.findById(newIssue.getProjectId()).get());
		} catch (IssueException | NoSuchElementException e) {
			throw new Exception(e.getMessage(), e);
		}
		
		//Optional Fields
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
	
	
	public void changeIssueStatus(Long issueId, EditIssueDTO newStatus) throws IssueException, StatusException {
		if(!issueRepository.findById(issueId).isPresent()) {
			throw new IssueException("Issue Not Found!");
		}
		if(newStatus.getNewId()==null || !statusRepository.findById(newStatus.getNewId()).isPresent()) {
			throw new StatusException("Invalid Status Value!");
		}
		Issue issue = issueRepository.findById(issueId).get();
		StatusModel nStatus = statusRepository.findById(newStatus.getNewId()).get();
		issue.setStatus(nStatus);
		issue.setLastUpdateDate(LocalDateTime.now());
		issueRepository.save(issue);
	}

	
	public void changeIssueType(Long issueId, EditIssueDTO newType) throws IssueException, TypeException {
		if(!issueRepository.findById(issueId).isPresent()) {
			throw new IssueException("Issue Not Found!");
		}
		if(newType.getNewId()==null || !typeRepository.findById(newType.getNewId()).isPresent()) {
			throw new TypeException("Invalid Type Value!");
		}
		Issue issue = issueRepository.findById(issueId).get();
		TypeModel nType = typeRepository.findById(newType.getNewId()).get();
		issue.setType(nType);
		issue.setLastUpdateDate(LocalDateTime.now());
		issueRepository.save(issue);
	}
	
	
	public void changeIssuePriority(Long issueId, EditIssueDTO newPriority) throws IssueException, PriorityException {
		if(!issueRepository.findById(issueId).isPresent()) {
			throw new IssueException("Issue Not Found!");
		}
		if(newPriority.getNewId()==null || !priorityRepository.findById(newPriority.getNewId()).isPresent()) {
			throw new PriorityException("Invalid Priority Value!");
		}
		Issue issue = issueRepository.findById(issueId).get();
		PriorityModel nPriority = priorityRepository.findById(newPriority.getNewId()).get();
		issue.setPriority(nPriority);
		issue.setLastUpdateDate(LocalDateTime.now());
		issueRepository.save(issue);
	}
	
	public void changeIssueAsignee(Long issueId, ChangeUserDTO newAsignee) throws IssueException, UserException {
		if(!issueRepository.findById(issueId).isPresent()) {
			throw new IssueException("Issue Not Found!");
		}
		if(newAsignee.getUsername()==null || !userRepository.findByUsername(newAsignee.getUsername()).isPresent()) {
			throw new UserException("User Not Found");
		}
		Issue issue = issueRepository.findById(issueId).get();
		User newUser = userRepository.findByUsername(newAsignee.getUsername()).get();
		issue.setAsigneeUser(newUser);
		issueRepository.save(issue);
	}
	
	public void changeIssueSprint(Long issueId, EditIssueDTO newSprint) throws IssueException, SprintException {
		if(!issueRepository.findById(issueId).isPresent()) {
			throw new IssueException("Issue Not Found!");
		}
		Issue issue = issueRepository.findById(issueId).get();
		if(newSprint.getNewId()!=null && !sprintRepository.findById(newSprint.getNewId()).isPresent()) {
			throw new SprintException("Sprint Not Found!");
		}
		if(newSprint.getNewId()==null) {
			issue.setSprint(null);
			issueRepository.save(issue);
			return;
		}
		issue.setSprint(sprintRepository.findById(newSprint.getNewId()).get());
		issueRepository.save(issue);
	}
	
	public void addDescription (Long issueId, DescriptionDTO description) throws IssueException {
		if(!issueRepository.findById(issueId).isPresent()) {
			throw new IssueException("Issue Not Found!");
		}
		Issue issue = issueRepository.findById(issueId).get();
		issue.setDescription(description.getDescription());
		issueRepository.save(issue);
	}
	
	@Transactional
	public void deleteIssueById(long issueId) throws IssueException {
		if(!issueRepository.findById(issueId).isPresent()) {
			throw new IssueException("Issue Not Found!");
		}
		worklogRepository.findAllByIssueId(issueId).forEach(wl -> worklogRepository.deleteById(wl.getId()));
		issueRepository.deleteById(issueId);
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
}
