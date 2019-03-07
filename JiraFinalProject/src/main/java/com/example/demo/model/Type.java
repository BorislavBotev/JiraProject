package com.example.demo.model;

import lombok.Getter;

@Getter
public enum Type {
	Task(1), Bug(2),Story(3), Epic(4);
	
	private long id;
	
	private Type(int id) {
		this.id = id;
	}
	
	private Type() {}
}
