package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ActivityDTO {
	private String username;
	private String content;
	private String type;
	private String subject;
	private LocalDateTime updateDate;
}
