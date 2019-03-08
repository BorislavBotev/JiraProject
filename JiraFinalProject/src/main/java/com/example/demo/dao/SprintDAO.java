package com.example.demo.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dto.CreateSprintDTO;
import com.example.demo.exceptions.InvalidDateException;
import com.example.demo.exceptions.InvalidNameException;
import com.example.demo.exceptions.InvalidSprintException;
import com.example.demo.exceptions.InvalidStatusexception;
import com.example.demo.model.Project;
import com.example.demo.model.Sprint;
import com.example.demo.model.StatusModel;
import com.example.demo.repositories.IssueRepository;
import com.example.demo.repositories.ProjectRepository;
import com.example.demo.repositories.SprintRepository;
import com.example.demo.repositories.StatusRepository;
@Component
public class SprintDAO {
	@Autowired
	private SprintRepository sprintRepository;
	@Autowired
	private IssueRepository issueRepository;
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


}
