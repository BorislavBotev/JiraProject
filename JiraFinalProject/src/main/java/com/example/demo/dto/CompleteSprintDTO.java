package com.example.demo.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.Issue;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CompleteSprintDTO {
	private String username;
	private LocalDate startDate;
	private LocalDateTime endDate;
	private List<String> finishedIssues;
	private List<String> issuesToBeDone;
	
	public void addFinishedIssues(List<Issue> issues) {
		this.finishedIssues=new ArrayList<>();
		if(issues!=null) {
			issues.forEach(i->this.finishedIssues.add(i.getSummary()));
		}
	}
	public void addIssuesToBeDone(List<Issue> issues) {
		this.issuesToBeDone=new ArrayList<>();
		if(issues!=null) {
			issues.forEach(i->this.issuesToBeDone.add(i.getSummary()));
		}
	}
}
