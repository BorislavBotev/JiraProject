package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class DetailedIssueDTO {
	private long id;
	private String summary;
	private String projectName;
	private String sprintName;
	private String description;
	private String type;
	private String priority;
	private String status;
	private LocalDateTime createDate;
	private LocalDateTime lastUpdateDate;
	private String reporterUserName;
	private String asigneeUserName;
}
