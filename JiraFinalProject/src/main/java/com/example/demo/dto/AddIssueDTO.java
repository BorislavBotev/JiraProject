package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AddIssueDTO {
	private String issueName;
	private String description;
	private Long statusId;
	private Long priorityId;
	private Long typeId;
	private Long reporterUserId;
	private Long asigneeUserId;
	private Long projectId;
	private Long sprintId;
	private Long componentId;
}
