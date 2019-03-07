package com.example.demo.dao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.check.UserCheck;
import com.example.demo.dto.CreateComponentDTO;
import com.example.demo.exceptions.InvalidComponentException;
import com.example.demo.model.Project;
import com.example.demo.repositories.ComponentRepository;
import com.example.demo.repositories.ProjectRepository;

@Component
public class ComponentDAO {
	@Autowired
	private ComponentRepository componentRepository;
	@Autowired
	private ProjectRepository projectRepository;
	public void createComponent(CreateComponentDTO component,Long id) throws InvalidComponentException {
		Project project=projectRepository.findById(id).get();
		com.example.demo.model.Component c=new com.example.demo.model.Component();
		boolean nameAlreadyexists=componentRepository.findAll().stream().filter(p->p.getName().equals(component.getName()))
				.findAny().isPresent();
		if(nameAlreadyexists) {
			throw new InvalidComponentException("Name already exists");
		}
		c.setName(component.getName());
		c.setDescription(component.getDescription());
		c.setProject(project);
		componentRepository.save(c);
	}
}
