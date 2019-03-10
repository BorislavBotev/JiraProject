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

import com.example.demo.exceptions.WorklogException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "worklogs")
public class Worklog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "worklog_id")
	private long id;
	
	@Column(name = "description")
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "issue_id")
	private Issue issue;
	
	@Column(name = "hours_spent")
	private int hoursSpent;
	
	@Column(name = "hours_remaining")
	private int hoursRemaining;
	
	@Setter
	@Column(name = "log_date")
	private LocalDateTime logDateTime;
	
	public Worklog(User user, Issue issue, String description, int hoursSpent, int hoursRemaining) throws WorklogException {
		this.setUser(user);
		this.setIssue(issue);
		this.setDescription(description);
		this.setHoursSpent(hoursSpent);
		this.setHoursRemaining(hoursRemaining);
		this.setLogDateTime(LocalDateTime.now());
	}
	
	public void setDescription(String description) throws WorklogException {
		if(description != null && description.trim().length()>0) {
			this.description = description;			
		} else throw new WorklogException("Invalid Description!");
	}
	
	public void setHoursSpent(int hoursSpent) throws WorklogException {
		if(hoursSpent >= 0) {
			this.hoursSpent = hoursSpent;			
		} else throw new WorklogException("Invalid Spent Hours!");
	}
	
	public void setHoursRemaining(int hoursRemaining) throws WorklogException {
		if(hoursRemaining >= 0) {
			this.hoursRemaining = hoursRemaining;			
		} else throw new WorklogException("Invalid Remaining Hours!");
	}
	
	public void setUser(User user) throws WorklogException {
		if(user != null) {
			this.user = user;			
		} else throw new WorklogException("Invalid User!");
	}
	
	public void setIssue(Issue issue) throws WorklogException {
		if(issue != null) {
			this.issue = issue;			
		} else throw new WorklogException("Invalid Issue!");
	}
}
