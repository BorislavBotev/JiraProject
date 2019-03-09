package com.example.demo.service;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class WorklogDTO {
	private long id;
	private String username;
	private String issueName;
	private String description;
	private int hoursSpent;
	private int hoursRemaining;
	private LocalDateTime logDateTime;
}
