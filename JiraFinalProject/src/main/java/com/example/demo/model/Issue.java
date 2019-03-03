package com.example.demo.model;

import java.time.LocalDate;

public class Issue {
	private Long id;
	private String name;
	private LocalDate createDate;
	private LocalDate updateDate;
	private String description;
	private Status status;
	private Type type;
	private Priority priority;
	private Sprint sprint;
	private Project project;
	private Component component;
	private User reporter;
	private User assigne;
	
}
