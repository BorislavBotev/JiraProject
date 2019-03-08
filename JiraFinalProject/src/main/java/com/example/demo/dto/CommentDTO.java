package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class CommentDTO {
	private String username;
	private String text;
	private String issueName;
	private LocalDateTime date;
}
