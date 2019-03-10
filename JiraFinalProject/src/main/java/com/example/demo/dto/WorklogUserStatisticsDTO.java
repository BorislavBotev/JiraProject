package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class WorklogUserStatisticsDTO {
	private Long id;
	private String username;
	private int totalHoursSpent;
}
