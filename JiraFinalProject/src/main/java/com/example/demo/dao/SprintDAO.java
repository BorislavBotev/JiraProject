package com.example.demo.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dto.CompleteSprintDTO;
import com.example.demo.exceptions.InvalidSprintException;
import com.example.demo.model.Issue;
import com.example.demo.model.Sprint;
import com.example.demo.repositories.IssueRepository;
import com.example.demo.repositories.SprintRepository;
import com.example.demo.repositories.UserRepository;
@Component
public class SprintDAO {
	@Autowired
	private SprintRepository sprintRepository;
	@Autowired
	private IssueRepository issueRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public void deleteSprintById(Long sprintId) throws InvalidSprintException {
		if(!sprintRepository.findById(sprintId).isPresent()) {
			throw new InvalidSprintException("No such sprint");
		}
		issueRepository.findAll().stream().
		filter(i->
		{
			if(i.getSprint()==null) {
				return false;
			}
				return i.getSprint().getId().equals(sprintId);
			}
		).
		forEach(i->i.setSprint(null));
		sprintRepository.deleteById(sprintId);
	}

	public CompleteSprintDTO completeSprint(Long sprintId) throws InvalidSprintException {
		if(!sprintRepository.findById(sprintId).isPresent()) {
			throw new InvalidSprintException("No such sprint");
		}
		Sprint sprint=sprintRepository.findById(sprintId).get();
		CompleteSprintDTO sprintDto=new CompleteSprintDTO();
		sprintDto.setEndDate(LocalDateTime.now());
		sprintDto.setStartDate(sprint.getStartDate());
		sprintDto.setUsername(sprint.getUser().getUsername());
		List<Issue> finishedIssues=issueRepository.findAll().stream().filter(i->i.getStatus().getName().equals("Done")).
				collect(Collectors.toList());
		sprintDto.addFinishedIssues(finishedIssues);
		List<Issue> issuesToBeDone=issueRepository.findAll().stream().filter(i->i.getStatus().getName().equals("In Progress")).
				collect(Collectors.toList());
		sprintDto.addIssuesToBeDone(issuesToBeDone);
		return sprintDto;
	}

}
