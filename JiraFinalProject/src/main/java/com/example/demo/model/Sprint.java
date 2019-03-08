package com.example.demo.model;

import java.time.LocalDate;
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

import com.example.demo.exceptions.InvalidDateException;
import com.example.demo.exceptions.InvalidNameException;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@Entity
@Table(name = "sprints")
public class Sprint {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sprint_id")
	private Long id;
	
	@Column(name = "sprint_name")
	private String name;
	
	@Column(name = "start_date")
	private LocalDate startDate;
	
	@Column(name = "end_date")
	private LocalDate endDate;
	
	@Transient //@OneToMany
	private Set<Issue> issues;
	
	@ManyToOne
	@JoinColumn(name = "status_id")
	private StatusModel status;
	
	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;
	

	public void setName(String name) throws InvalidNameException {
		if(name==null || name.trim().length()==0) {
			throw new InvalidNameException("Invalid name");
		}
		this.name = name;
	}


	public void setEndDate(LocalDate endDate) throws InvalidDateException {
		if(endDate.isBefore(this.startDate)) {
			throw new InvalidDateException("Invalid date");
		}
		this.endDate=endDate;
	}
}
