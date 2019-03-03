package com.example.demo.model;

import java.time.LocalDate;
import java.util.List;

public class Sprint {
	private Long id;
	private String name;
	private LocalDate startDate;
	private LocalDate endDate;
	private Status status;
	private List<Issue> issues;
}
