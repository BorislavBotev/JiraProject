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

import com.example.demo.exceptions.IssueException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
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
	
	@Setter
	@Column(name = "description")
	private String description;
	
	@Setter
	@Column(name = "create_date")
	private LocalDateTime createDate;
	
	@Setter
	@Column(name = "update_date")
	private LocalDateTime lastUpdateDate;
	
	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;
	
	@Setter
	@ManyToOne
	@JoinColumn(name = "sprint_id")
	private Sprint sprint;
	
	@ManyToOne
	@JoinColumn(name = "reporter_user_id")
	private User reporterUser;
	
	@ManyToOne
	@JoinColumn(name = "asignee_user_id")
	private User asigneeUser;
	
	@Setter
	@ManyToOne
	@JoinColumn(name = "component_id")
	private Component component;

	public void setSummary(String summary) throws IssueException {
		if(summary!=null && summary.trim().length()>0) {
			this.summary = summary;
		} else throw new IssueException("Invalid Issue Name!");
	}

	public void setType(TypeModel type) throws IssueException {
		if(type!=null && type.getName()!=null && type.getName().trim().length()>0) {
			this.type = type;			
		} else throw new IssueException("Invalid Issue Type!");
	}

	public void setStatus(StatusModel status) throws IssueException {
		if(status!=null && status.getName()!=null && status.getName().trim().length()>0) {
			this.status = status;			
		} else throw new IssueException("Invalid Issue Status!");
	}

	public void setPriority(PriorityModel priority) throws IssueException {
		if(priority!=null && priority.getName()!=null && priority.getName().trim().length()>0) {
			this.priority = priority;			
		} else throw new IssueException("Invalid Issue Priority!");
	}

	public void setProject(Project project) throws IssueException {
		if(project != null) {
			this.project = project;			
		} else throw new IssueException("Invalid Project!");
	}

	public void setReporterUser(User reporterUser) throws IssueException {
		if(reporterUser != null) {
			this.reporterUser = reporterUser;			
		} else throw new IssueException("Invalid Reporter User");
	}
	
	public void setAsigneeUser(User asigneeUser) throws IssueException {
		if(asigneeUser != null) {
			this.asigneeUser = asigneeUser;			
		} else throw new IssueException("Invalid Asignee User");
	}
}
