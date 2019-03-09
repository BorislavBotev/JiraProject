package com.example.demo.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Entity
@NoArgsConstructor
@Table(name = "projects")
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "project_id")
	private Long id;
	
	@Column(name = "project_name")
	private String name;
	

//	@Transient //@ManyToMany
//	private Set<User> users=new HashSet<>();
//	
//	@Transient //@OneToMany
//	private List<Issue> issues=new ArrayList<>();
//	
//	@OneToMany(mappedBy="project")
//	private List<Component> components;
//	
//	@Transient //@OneToMany
//	private List<Sprint> sprints=new ArrayList<>();

//	@ManyToMany
//	@JoinTable(
//	  name = "users_has_projects", 
//	  joinColumns = @JoinColumn(name = "project_id"), 
//	  inverseJoinColumns = @JoinColumn(name = "user_id"))
	@Transient
	private Set<User> users=new HashSet<>();
	
	@Transient //@OneToMany
	private List<Issue> issues=new ArrayList<>();
	
	@Transient //@OneToMany
	private List<Component> components=new ArrayList<Component>();
	
	@Transient //@OneToMany
	private List<Sprint> sprints=new ArrayList<>();
	
	public Project(Long id,String name) {
		this.id=id;
		this.name=name;
	}
}
