package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ComponentInfoDTO {
	private String name;
	private String description;
	private List<String> issueNames=new ArrayList<>();
	public void addIssueName(String name) {
		if(name!=null) {
			issueNames.add(name);
		}
	}
}
