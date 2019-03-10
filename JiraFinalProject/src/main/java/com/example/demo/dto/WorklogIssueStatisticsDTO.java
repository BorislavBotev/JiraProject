package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class WorklogIssueStatisticsDTO {
	private Long issueId;
	private String issueName;
	private int totalHoursSpent;
}
