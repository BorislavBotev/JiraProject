package com.example.demo.service;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CreateSprintDTO;
import com.example.demo.exceptions.InvalidDateException;
import com.example.demo.exceptions.InvalidNameException;
import com.example.demo.exceptions.InvalidStatusexception;
import com.example.demo.model.Project;
import com.example.demo.model.Sprint;
import com.example.demo.model.StatusModel;
import com.example.demo.repositories.IssueRepository;
import com.example.demo.repositories.ProjectRepository;
import com.example.demo.repositories.SprintRepository;
import com.example.demo.repositories.StatusRepository;
@Service
public class SprintService {
		@Autowired
		private SprintRepository sprintRepository;
		@Autowired
		private ProjectRepository projectRepository;
		@Autowired
		private StatusRepository statusRepository;
		@Autowired
		private IssueRepository issueRepository;
		
		public void createSprint(CreateSprintDTO sprint, Long id) throws InvalidNameException, InvalidDateException, InvalidStatusexception {
			LocalDate date=sprint.getEndDate();
			System.out.println(date);
			
			Project project=projectRepository.findById(id).get();
			Sprint sp=new Sprint();
			boolean nameAlreadyexists=sprintRepository.findAll().stream().filter(s->s.getName().equals(sprint.getName()))
					.findAny().isPresent();
			if(nameAlreadyexists) {
				throw new InvalidNameException("Name already exists");
			}
			sp.setName(sprint.getName());
			sp.setStartDate(sprint.getStartDate());
			sp.setEndDate(sprint.getEndDate());
			System.out.println(sprint.getStatusId());
			StatusModel status=statusRepository.getOne(sprint.getStatusId());
			if(status==null) {
				throw new InvalidStatusexception("invalid status");
			}
			sp.setStatus(status);
			sp.setProject(project);
			System.out.println(sp);
			project.setSprints(Arrays.asList(sp));
			sprintRepository.save(sp);
		}
		
		

	
	
}
