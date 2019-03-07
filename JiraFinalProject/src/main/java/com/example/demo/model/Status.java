package com.example.demo.model;

public enum Status {
	ToDo(1), InProgress(2), Done(3);
	
	private long id;
	
	private Status(int id) {
		this.id = id;
	}
}
