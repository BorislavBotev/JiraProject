package com.example.demo.service;

import java.time.LocalDate;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CreateSprintDTO;
import com.example.demo.exceptions.InvalidDateException;
import com.example.demo.exceptions.InvalidNameException;
import com.example.demo.exceptions.InvalidSprintException;
import com.example.demo.exceptions.InvalidStatusexception;
import com.example.demo.model.Project;
import com.example.demo.model.Sprint;
import com.example.demo.model.StatusModel;
import com.example.demo.model.User;
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
		/**
		 * Creates a new sprint in the project given by an id
		 * 
		 * @param sprint - CreateSprintDTO
		 * @param id - Id of the project
		 * @param user - the user creating the sprint
		 * @throws InvalidNameException
		 * @throws InvalidDateException
		 * @throws InvalidStatusexception
		 */
		public void createSprint(CreateSprintDTO sprint, Long id,User user) throws InvalidNameException, InvalidDateException, InvalidStatusexception {
		
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
			if(!statusRepository.findById(sprint.getStatusId()).isPresent()) {
				throw new InvalidStatusexception("invalid status");
			}
			StatusModel status = statusRepository.findById(sprint.getStatusId()).get();
			sp.setStatus(status);
			sp.setProject(project);
			sp.setUser(user);
			sprintRepository.save(sp);
		}
	
}
