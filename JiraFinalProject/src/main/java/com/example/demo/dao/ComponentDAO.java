package com.example.demo.dao;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dto.ComponentInfoDTO;
import com.example.demo.dto.CreateComponentDTO;
import com.example.demo.exceptions.InvalidComponentException;
import com.example.demo.model.Project;
import com.example.demo.repositories.ComponentRepository;
import com.example.demo.repositories.IssueRepository;
import com.example.demo.repositories.ProjectRepository;

@Component
public class ComponentDAO {
	@Autowired
	private ComponentRepository componentRepository;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private IssueRepository issueRepository;
	
	
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
	@Transactional
	public void deleteComponentById(Long componentId) throws InvalidComponentException {
		if(!componentRepository.findById(componentId).isPresent()) {
			throw new InvalidComponentException("No such component");
		}
		issueRepository.findAll().stream().
		filter(i->
		{
			if(i.getComponent()==null) {
				return false;
			}
			return i.getComponent().getId().equals(componentId);
		}
		).
		forEach(i->i.setComponent(null));
		componentRepository.deleteById(componentId);
	}
	@Transactional
	public ComponentInfoDTO showInfoById(Long componentId) throws InvalidComponentException {
		if(!componentRepository.findById(componentId).isPresent()) {
			throw new InvalidComponentException("No such component");
		}
		ComponentInfoDTO dto=new ComponentInfoDTO();
		com.example.demo.model.Component c=componentRepository.getOne(componentId);
		dto.setName(c.getName());
		if(c.getDescription()!=null) {
			dto.setDescription(c.getDescription());
		}
		if(issueRepository.findAll().stream().filter(i->i.getComponent().getId().equals(componentId)).findFirst().isPresent()) {
			issueRepository.findAll().stream().filter(i->{
				if(i.getComponent()==null) {
					return false;
				}
					return i.getComponent().getId().equals(componentId);
				
			}).
			forEach(i->dto.addIssueName(i.getSummary()));
		}
		return dto;
	}
	
}
