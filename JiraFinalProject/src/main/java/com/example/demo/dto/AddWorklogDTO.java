package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AddWorklogDTO {
	private Integer hoursSpent;
	private Integer hoursRemaining;
	private String description;
}
