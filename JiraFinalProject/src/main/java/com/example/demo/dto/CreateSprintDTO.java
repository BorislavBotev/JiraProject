package com.example.demo.dto;

import java.time.LocalDate;

import lombok.Data;


@Data
public class CreateSprintDTO {
	private String name;
	private LocalDate startDate;
	private LocalDate endDate;
	private Long statusId;
	
	
}
