package com.example.demo.model;

import lombok.Getter;

@Getter
public enum Priority {
	Highest(1), High(2), Medium(3), Low(4), Lowest(5);
	
	private long id;
	
	private Priority(int id) {
		this.id = id;
	}
	
	private Priority() {}
}
