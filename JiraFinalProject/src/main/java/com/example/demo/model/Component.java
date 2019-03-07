package com.example.demo.model;

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

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "components")
public class Component {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "component_id")
	private long id;
	
	@Column(name = "component_name")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;
	
	@Transient //@OneToMany
	private Set<Issue> issues;
}
