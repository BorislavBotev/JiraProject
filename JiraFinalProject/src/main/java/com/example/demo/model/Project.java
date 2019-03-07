package com.example.demo.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "projects")
public class Project {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "project_id")
	private long id;
	
	@Column(name = "project_name")
	private String name;
	
	@Transient //@ManyToMany
	private Set<User> users;
	
	@Transient //@OneToMany
	private Set<Issue> issues;
	
	@Transient //@OneToMany
	private Set<Component> components;
	
}
