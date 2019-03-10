package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
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
