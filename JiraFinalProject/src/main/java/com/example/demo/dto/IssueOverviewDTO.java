package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
@AllArgsConstructor
public class IssueOverviewDTO {
	private Long id;
	private String summary;
	private String projectName;
	private String type;
	private String priority;
}
