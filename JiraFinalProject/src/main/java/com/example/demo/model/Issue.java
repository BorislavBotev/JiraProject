package com.example.demo.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "issues")
public class Issue {
	
	@Id
	@Column(name = "issue_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "issue_name")
	private String summary;
	
	@ManyToOne
	@JoinColumn(name = "type_id")
	private TypeModel type;
	
	@ManyToOne
	@JoinColumn(name = "status_id")
	private StatusModel status;
	
	@ManyToOne
	@JoinColumn(name = "priority_id")
	private PriorityModel priority;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "create_date")
	private LocalDateTime createDate;
	
	@Column(name = "update_date")
	private LocalDateTime lastUpdateDate;
	
	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;
	
	@ManyToOne
	@JoinColumn(name = "sprint_id")
	private Sprint sprint;
	
	@ManyToOne
	@JoinColumn(name = "reporter_user_id")
	private User reporterUser;
	
	@ManyToOne
	@JoinColumn(name = "asignee_user_id")
	private User asigneeUser;
	
	@ManyToOne
	@JoinColumn(name = "component_id")
	private Component component;
}
