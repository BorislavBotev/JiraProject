package com.example.demo.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class CreateSprintDTO {
	private String name;
	private LocalDate startDate;
	private LocalDate endDate;
	private Long statusId;
	
	
}
