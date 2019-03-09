package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.Component;
import com.example.demo.model.Issue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@AllArgsConstructor
public class ViewComponentDto {
	private Long id;
	private String name;
	private String description;
	
	public ViewComponentDto(Component c) {
		this(c.getId(), c.getName(), c.getDescription());
	}
}
