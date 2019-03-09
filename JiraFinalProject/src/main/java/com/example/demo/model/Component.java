package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.example.demo.exceptions.InvalidComponentException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "components")
public class Component {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "component_id")
	private Long id;
	
	@Column(name = "component_name")
	private String name;

	
	@Column(name = "description")
	private String description;
	
	
	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;
	
	@Transient //@OneToMany
	private List<Issue> issues=new ArrayList<Issue>();
	
	
	public void setName(String name) throws InvalidComponentException {
		if(name==null || name.trim().length()==0) {
			throw new InvalidComponentException("invalid name");
		}
		this.name=name;
	}
	public void setProject(Project project) {
		this.project=project;
	}
	
	
}
