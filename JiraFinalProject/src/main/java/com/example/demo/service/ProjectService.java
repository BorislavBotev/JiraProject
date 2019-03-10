package com.example.demo.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserDAO;
import com.example.demo.repositories.ComponentRepository;
import com.example.demo.repositories.IssueRepository;
import com.example.demo.repositories.ProjectRepository;
import com.example.demo.repositories.SprintRepository;
import com.example.demo.repositories.UserRepository;

@Service
public class ProjectService {
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private IssueRepository issueRepository;
	@Autowired
	private SprintRepository sprintRepository;
	@Autowired
	private ComponentRepository componentRepository;
	
	
	@Transactional
	public void deleteProjectById(Long id) {
		issueRepository.findAll().stream().
		filter(i->i.getProject().getId().equals(id)).
		forEach(i->issueRepository.deleteById(i.getId()));
		
		componentRepository.findAll().stream().
		filter(c->c.getProject().getId().equals(id)).
		forEach(c->componentRepository.deleteById(c.getId()));
		
		sprintRepository.findAll().stream().
		filter(s->s.getProject().getId().equals(id)).
		forEach(s->sprintRepository.deleteById(s.getId()));
		
		projectRepository.deleteById(id);		
	}
}
